package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        Long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        Long count2 = memberJpaRepository.count();
        assertThat(count2).isEqualTo(0);


    }

    @Test
    public void paging() {
        //given

        memberJpaRepository.save(new Member(10, "member1"));
        memberJpaRepository.save(new Member(10, "member2"));
        memberJpaRepository.save(new Member(10, "member3"));
        memberJpaRepository.save(new Member(10, "member4"));
        memberJpaRepository.save(new Member(10, "member5"));

        int age = 10;
        int offset = 1;
        int limit = 3;

        List<Member> members = memberJpaRepository.findByPage(10, 0, 3);
        long total = memberJpaRepository.totalCount(10);

        //then
        assertThat(members.size()).isEqualTo(3);
        assertThat(total).isEqualTo(5);

    }

    @Test
    public void bulkUpdate() {
        memberJpaRepository.save(new Member(10, "member1"));
        memberJpaRepository.save(new Member(19, "member2"));
        memberJpaRepository.save(new Member(20, "member3"));
        memberJpaRepository.save(new Member(21, "member4"));
        memberJpaRepository.save(new Member(40, "member5"));


        //when
        int resultCount = memberJpaRepository.bulkAgePlus(20);


        assertThat(resultCount).isEqualTo(3);
    }



}
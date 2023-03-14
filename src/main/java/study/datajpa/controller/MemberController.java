package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {

        return member.getUsername();
    }

    @GetMapping("/members2")
    public Page<MemberDto> list(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername()));
        return map;
    }

//    @PostConstruct
//    public void init() {
//        memberRepository.save(new Member("userA"));
//        memberRepository.save(new Member("userB"));
//        memberRepository.save(new Member("userC"));
//        memberRepository.save(new Member("userD"));
//        memberRepository.save(new Member("userE"));
//        memberRepository.save(new Member("userF"));
//        memberRepository.save(new Member("userG"));
//        memberRepository.save(new Member("userH"));
//        memberRepository.save(new Member("userI"));
//        memberRepository.save(new Member("userJ"));
//        memberRepository.save(new Member("userK"));
//    }
    
}

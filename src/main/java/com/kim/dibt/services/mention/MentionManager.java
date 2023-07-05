package com.kim.dibt.services.mention;

import com.kim.dibt.core.utils.email.MailService;
import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.SuccessDataResult;
import com.kim.dibt.models.Mention;
import com.kim.dibt.models.Post;
import com.kim.dibt.repo.MentionRepository;
import com.kim.dibt.security.repo.UserRepository;
import com.kim.dibt.services.mention.dtos.AddMentionDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MentionManager implements MentionService {
    private final MentionRepository mentionRepository;
    private final UserRepository userRepository;
    private final MailService mailService;

    @Override
    public DataResult<List<AddMentionDto>> addAll(List<String> mentions, Post post) {
        List<AddMentionDto> addMentionDtos = new ArrayList<>();
        mentions.forEach(mention -> userRepository.findByUsername(mention).ifPresent(user -> {
            Mention m = new Mention();
            m.setUser(user);
            m.setPost(post);
            Mention save = mentionRepository.save(m);
            AddMentionDto addMentionDto = new AddMentionDto();
            addMentionDto.setId(save.getId());
            addMentionDto.setUsername(user.getUsername());
            addMentionDto.setPostId(post.getId());
            addMentionDto.setUserId(user.getId());
            addMentionDtos.add(addMentionDto);
            try {
                mailService.sendMail(user.getEmail(), "DIBT - Yeni bir gönderide etiketlendiniz", "Merhaba " + user.getUsername() + ", yeni bir gönderide etiketlendiniz. Gönderiye gitmek için tıklayın: http://localhost:8080/post/" + post.getId());
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }));
        return SuccessDataResult.of(addMentionDtos);
    }

    @Override
    public void deleteAll(List<Mention> mentions) {
        mentionRepository.deleteAll(mentions);
    }

    @Override
    public void deleteById(Long id) {
        mentionRepository.deleteById(id);
    }
}


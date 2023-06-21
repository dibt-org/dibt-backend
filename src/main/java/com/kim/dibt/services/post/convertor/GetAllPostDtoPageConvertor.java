package com.kim.dibt.services.post.convertor;

import com.kim.dibt.core.models.PageModel;
import com.kim.dibt.models.Media;
import com.kim.dibt.models.Post;
import com.kim.dibt.services.post.dtos.GetAllPostDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class GetAllPostDtoPageConvertor implements Function<Page<Post>, PageModel<GetAllPostDto>> {
    @Value("${api.v1.posts.get-all}")
    String link;
    @Override
    public PageModel<GetAllPostDto> apply(Page<Post> page) {
        PageModel<GetAllPostDto> pageModel = new PageModel<>();
        pageModel.setData(page.map(post -> {
            GetAllPostDto getAllPostDto = new GetAllPostDto();
            getAllPostDto.setId(post.getId());
            getAllPostDto.setTitle(post.getTitle());
            getAllPostDto.setContent(post.getContent());
            getAllPostDto.setUsername(post.getUser().getUsername());
            getAllPostDto.setMediaUrls(post.getMedias().stream().map(Media::getUrl).toList());
            getAllPostDto.setMentions(post.getMentions().stream().map(mention -> mention.getUser().getUsername()).toList());
            return getAllPostDto;
        }).toList());
        pageModel.setTotalPages(page.getTotalPages());
        pageModel.setTotalElements(page.getTotalElements());
        pageModel.setPageNumber(page.getNumber());
        pageModel.setPageSize(page.getSize());
        pageModel.setHasNext(page.hasNext());
        pageModel.setHasPrevious(page.hasPrevious());
        int previousPageNumber = page.hasPrevious() ? page.previousPageable().getPageNumber() : 0;
        int previousPageSize = page.hasPrevious() ? page.previousPageable().getPageSize() : 0;
        int nextPageNumber = page.hasNext() ? page.nextPageable().getPageNumber() : 0;
        int nextPageSize = page.hasNext() ? page.nextPageable().getPageSize() : 0;
        pageModel.setNextLink(String.format(link, nextPageNumber, nextPageSize));
        pageModel.setPreviousLink(String.format(link, previousPageNumber, previousPageSize));
        pageModel.setSelfLink(String.format(link, page.getNumber(), page.getSize()));
        return pageModel;
    }

}

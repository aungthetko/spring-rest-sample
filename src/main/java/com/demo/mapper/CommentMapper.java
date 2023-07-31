package com.demo.mapper;

import com.demo.dto.CommentDTO;
import com.demo.entity.comment.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static List<CommentDTO> mapToCommentDTO(List<Comment> commentList){
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(Comment comment : commentList){
            CommentDTO commentDTO = new CommentDTO(comment.getId(), comment.getComment());
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}

package com.dmuIt.domain.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

public class CommunityController
{
    @RequestMapping("/commu/openBoardWrite")		//게시글 작성 화면 호출
    public String openBoardWrite() throws Exception{
        return "/boardWrite";
    }

    @RequestMapping("/commu/insertBoard")		//작성된 게시글 등록 기능 메소드, html의 form 태그 action에서 입력한 주소
    public String insertBoard(@ModelAttribute BoardDto board) throws Exception{
        boardService.insertBoard(board);
        return "redirect:/board/openBoardList.do";	//게시글 리스트로 이동
    }
}

package com.zsx.md.service;

import com.zsx.md.entity.Mnote;

import java.util.List;

public interface NoteService {

    List<Mnote> getNoteListByUserId(Integer userId);

}

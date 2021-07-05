package com.studb.poemNote.repository.backup;

import java.util.List;

import com.studb.poemNote.domain.backup.Backup;

public interface BackupRepository {
    
    List<Backup> findAll();

    void doBackUp(Backup backup);
    
}

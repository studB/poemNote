package com.studb.poemNote.domain.backup;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Backup {
    
    private Long id;
    private int categoryId;
    private String textId;
    private int publishIndex;
    private String title;
    private String body;
    private String tag;
    private String completedStatus;
    private String writtenStatus;
    private String valueStatus;
    private Timestamp time; 

    public static class Builder {
        private Long id;
        private int categoryId;
        private String textId;
        private int publishIndex;
        private String title;
        private String body;
        private String tag;
        private String completedStatus;
        private String writtenStatus;
        private String valueStatus;
        private Timestamp time;

        public Builder(){};

        public Builder(Backup backup){
            this.id = backup.id;
            this.categoryId = backup.categoryId;
            this.textId = backup.textId;
            this.publishIndex = backup.publishIndex;
            this.title = backup.title;
            this.body = backup.body;
            this.tag = backup.tag;
            this.completedStatus = backup.completedStatus;
            this.writtenStatus = backup.writtenStatus;
            this.valueStatus = backup.valueStatus;
            this.time = backup.time;
        }

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder categoryId(int categoryId){
            this.categoryId = categoryId;
            return this;
        }

        public Builder textId(String textId){
            this.textId = textId;
            return this;
        }

        public Builder publishIndex(int publishIndex){
            this.publishIndex = publishIndex;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder body(String body){
            this.body = body;
            return this;
        }

        public Builder tag(String tag){
            this.tag = tag;
            return this;
        }

        public Builder completedStatus(String completedstatus){
            this.completedStatus = completedstatus;
            return this;
        }

        public Builder writtenStatus(String writtenStatus){
            this.writtenStatus = writtenStatus;
            return this;
        }

        public Builder valuStatus(String valueStatus){
            this.valueStatus = valueStatus;
            return this;
        }

        public Builder time(Timestamp time){
            this.time = time;
            return this;
        }

        public Backup build(){
            return new Backup(
                id, categoryId, textId, publishIndex, title, body, tag, completedStatus, writtenStatus, valueStatus, time
            );
        }

    }

}

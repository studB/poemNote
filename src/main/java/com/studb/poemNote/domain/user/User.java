package com.studb.poemNote.domain.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    
    @EqualsAndHashCode.Include
    private Long id;
    private String role;
    private int accessPower;
    private String key;
    private Boolean editable;
    private Boolean readable;

    public static class Builder {
        private Long id;
        private String role;
        private int accessPower;
        private String key;
        private Boolean editable;
        private Boolean readable;

        public Builder(){};

        public Builder(User user){
            this.id = user.id;
            this.role = user.role;
            this.accessPower = user.accessPower;
            this.key = user.key;
            this.editable = user.editable;
            this.readable = user.readable;
        }

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder role(String role){
            this.role = role;
            return this;
        }

        public Builder accessPower(int accessPower){
            this.accessPower = accessPower;
            return this;
        }

        public Builder key(String key){
            this.key = key;
            return this;
        }

        public Builder editable(Boolean editable){
            this.editable = editable;
            return this;
        }

        public Builder readable(Boolean readable){
            this.readable = readable;
            return this;
        }

        public User build(){
            return new User(
                id, role, accessPower, key, editable, readable
            );
        }
    }

}

package com.yonyou.beisendemo.exception;

import com.yonyou.beisendemo.vo.Status;
import com.yonyou.beisendemo.vo.constants.StatusEnum;
import lombok.Getter;

public class ForumAdviceException extends RuntimeException{

    @Getter
    private Status status;

    public ForumAdviceException(Status status){
        this.status = status;
    }

    public ForumAdviceException(int code , String msg){
        this.status = Status.newStatus(code, msg);
    }

    public ForumAdviceException(StatusEnum status, Object... args){
        this.status = Status.newStatus(status, args);
    }
}

package com.booth.currencyex.controllers.answers;

public class OtpAnswer {

    private String errorMessage;

    public OtpAnswer(){

    }
    public OtpAnswer(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    public boolean isError(){
        return (errorMessage == null);
    }
}

package com.yonyou.beisendemo.decorator;

public class DelegatedExcoterService implements Excoter {

    private final Excoter e;

    DelegatedExcoterService(Excoter ex){
        e = ex;
    }

    @Override
    public void execute(Runnable run) {
        e.execute(run);
    }
}

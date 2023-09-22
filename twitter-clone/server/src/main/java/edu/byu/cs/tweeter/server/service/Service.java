package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.factory.IFactory;
import edu.byu.cs.tweeter.server.factory.TweeterFactory;

public class Service {

    private TweeterFactory tweeterFactory;

    public TweeterFactory getTweeterFactory() {
        if (tweeterFactory == null) {
            tweeterFactory = new TweeterFactory();
        }
        return tweeterFactory;
    }

    public Service() {
        this.tweeterFactory = new TweeterFactory();
    }


    private IFactory factory;


    public Service(IFactory factory) {
        this.factory = factory;
    }

    public IFactory getFactory() {
        return factory;
    }




}

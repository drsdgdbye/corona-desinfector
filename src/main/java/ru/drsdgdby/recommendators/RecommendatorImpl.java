package ru.drsdgdby.recommendators;

import ru.drsdgdby.annotations.InjectProperty;

@Deprecated
public class RecommendatorImpl implements Recommendator {
    @InjectProperty("wisky")
    private String alcohol;

    public RecommendatorImpl() {
        System.out.println("recommendator was created");
    }

    @Override
    public void recommendate() {
        System.out.println("drink " + alcohol);
    }
}

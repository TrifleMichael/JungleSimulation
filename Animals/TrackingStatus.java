package Animals;

public enum TrackingStatus {
    NONE, CHOSEN, CHILD, DESCENDANT;

    public TrackingStatus strongerStatus(TrackingStatus other) {
        if (this == CHOSEN || other == CHOSEN)
            return CHOSEN;
        if (this == CHILD || other == CHILD)
            return CHILD;
        if (this == DESCENDANT || other == DESCENDANT)
            return DESCENDANT;
        else
            return NONE;
    }
}

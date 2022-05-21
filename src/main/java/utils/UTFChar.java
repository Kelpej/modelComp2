package utils;

public enum UTFChar {
    TRUE("✓"),
    FALSE("\uD800\uDD02");

    final String value;

    UTFChar(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

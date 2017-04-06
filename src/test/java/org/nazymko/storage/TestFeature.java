package org.nazymko.storage;

/**
 * Created by nazymko.patronus@gmail.com
 */
public enum TestFeature {
    EMULATE_DUPLICATE,
    EMULATE_LOST_PACKAGE;

    public boolean isEnabled() {
        return enabled;
    }

    private boolean enabled;

    TestFeature(boolean enabled) {
        this.enabled = enabled;
    }

    TestFeature() {
        enabled = false;
    }


}

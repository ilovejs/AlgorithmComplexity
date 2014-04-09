package org.jetbrains.complexity;

public enum Complexity {
    CONSTANT,
    LINEAR,
    LOG_LINEAR,
    QUADRATIC;

    public static Complexity getInstance(String annotationName) {
        if (annotationName.equals("org.jetbrains.complexity.Constant"))
            return Complexity.LINEAR;
        else if (annotationName.equals("org.jetbrains.complexity.Linear"))
            return Complexity.LINEAR;
        else if (annotationName.equals("org.jetbrains.complexity.LogLinear"))
            return Complexity.LOG_LINEAR;
        else if (annotationName.equals("org.jetbrains.complexity.Quadratic"))
            return Complexity.QUADRATIC;
        else throw new UnsupportedOperationException();
    }

    public boolean isInbound(int nestedLoopsNumber) {
        switch (this) {
            case CONSTANT:
                return nestedLoopsNumber < 1;
            case LINEAR:
                return nestedLoopsNumber < 2;
            case LOG_LINEAR:
                return nestedLoopsNumber < 2;
            case QUADRATIC:
                return nestedLoopsNumber < 3;
        }
        return true;
    }
}

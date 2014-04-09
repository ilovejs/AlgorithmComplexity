package org.jetbrains.complexity;

public enum Complexity {
    CONSTANT,
    DOUBLE_LOGARITHMIC,
    LOGARITHMIC,
    LINEAR,
    LOG_LINEAR,
    QUADRATIC,
    EXPONENTIAL,
    FACTORIAL;

    public static Complexity getInstance(String annotationName) {
        if (annotationName.equals("org.jetbrains.complexity.Constant"))
            return Complexity.LINEAR;
        else if (annotationName.equals("org.jetbrains.complexity.DoubleLogarithmic"))
            return Complexity.DOUBLE_LOGARITHMIC;
        else if (annotationName.equals("org.jetbrains.complexity.Logarithmic"))
            return Complexity.LOGARITHMIC;
        else if (annotationName.equals("org.jetbrains.complexity.Linear"))
            return Complexity.LINEAR;
        else if (annotationName.equals("org.jetbrains.complexity.LogLinear"))
            return Complexity.LOG_LINEAR;
        else if (annotationName.equals("org.jetbrains.complexity.Quadratic"))
            return Complexity.QUADRATIC;
        else if (annotationName.equals("org.jetbrains.complexity.Exponential"))
            return Complexity.EXPONENTIAL;
        else if (annotationName.equals("org.jetbrains.complexity.Factorial"))
            return Complexity.FACTORIAL;
        else throw new UnsupportedOperationException();
    }

    public boolean allowsNestedLoops(int nestedLoopsNumber) {
        switch (this) {
            case CONSTANT:
                return nestedLoopsNumber < 1;
            case DOUBLE_LOGARITHMIC:
                return nestedLoopsNumber < 2;
            case LOGARITHMIC:
                return nestedLoopsNumber < 2;
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

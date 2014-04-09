Algorithm Complexity
===================

A plugin for IntelliJ IDEA which detects trivial algorithm complexity problems in your code.

## Example:

```java
static void bubbleSort(@LogLinear int[] array) {
    boolean f;
    int swap;
    int min;
    for (int j = 0; j < array.length - 1; j++) {
        f = false;
        min = j;
        for (int i = j; i < (array.length - j - 1); i++) { // detects non-linear complexity
            if (array[i] > array[i + 1]) {
                swap = array[i];
                array[i] = array[i + 1];
                array[i + 1] = swap;
                f = true;
            }
            if (array[i] < array[min])
                min = i;
        }
        if (!f)
            break;
        if (min != j) {
            swap = array[j];
            array[j] = array[min];
            array[min] = swap;
        }
    }
}

static Map<Integer, Integer> allDuplicates(@Linear int[] arr) {
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    for (int i = 0; i < arr.length; i++) {
        map.put(arr[i], 1);
        for (int j = 0; j < arr.length; j++) { // detects non-linear complexity
            if (i != j && arr[i] == arr[j]) {
                map.put(arr[i], map.get(arr[i]) + 1);
            }
        }
    }
    return map;
}

static int duplicates(@Linear int[] arr) {
    int count = 0;
    for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr.length; i++) { // detects non-linear complexity
            if (i != j && arr[i] == arr[j]) {
                count++;
            }
        }
    }
    return count;
}

static void insertionSort(@LogLinear int[] arr) { // correct
    for (int i = 1; i < arr.length; i++) {
        int valueToSort = arr[i];
        int j = i;
        while (j > 0 && arr[j - 1] > valueToSort) {
            arr[j] = arr[j - 1];
            j--;
        }
        arr[j] = valueToSort;
    }
}

static int max(@Linear int[] arr) {
    insertionSort(arr); // TODO: detect non-linear complexity
    return arr[arr.length - 1];
}

static int factorial(@Linear int n) {
    if (n == 0) return 1;
    return n * factorial(n - 1); // TODO: detect exponential complexity
}

static HashMap<Integer, Integer> cache = new HashMap<Integer, Integer>();

static int cachedFactorial(@Linear int n) {
    Integer ret;

    if (n == 0) return 1;
    if (null != (ret = cache.get(n))) return ret;
    ret = n * cachedFactorial(n - 1);
    cache.put(n, ret);
    return ret;
}

static double gamma(@Constant double z) { // correct
    double tmp1 = Math.sqrt(2 * Math.PI / z);
    double tmp2 = z + 1.0 / (12 * z - 1.0 / (10 * z));
    tmp2 = Math.pow(z / Math.E, z);
    tmp2 = Math.pow(tmp2 / Math.E, z);
    return tmp1 * tmp2;
}

static int[] factorials(@Linear int[] arr) {
    int[] factorials = new int[arr.length];
    for (int i = 0; i < arr.length; i++) {
        factorials[i] = factorial(arr[i]); // TODO: detects non-linear complexity
    }
    return factorials;
}
```

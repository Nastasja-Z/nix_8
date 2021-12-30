package ua.com.alevel.mathset;

import java.util.Arrays;

public class MathSet<NUM extends Number> {

    private final int INITIAL_SIZE = 10;
    private int sizeOfArray;
    private int currentLastIndex;
    private Number[] numbers;

    public MathSet() {
        this.numbers = new Number[INITIAL_SIZE];
        this.currentLastIndex = 0;
        this.sizeOfArray = INITIAL_SIZE;
    }

    public MathSet(int capacity) {
        this.currentLastIndex = 0;
        this.sizeOfArray = capacity;
        this.numbers = new Number[sizeOfArray];
    }

    public MathSet(Number[] numbers) {
        if (numbers == null) {
            this.numbers = new Number[sizeOfArray];
            this.currentLastIndex = 0;
        } else {
            this.sizeOfArray = numbers.length;
            this.numbers = numbers;
            this.currentLastIndex = numbers.length;
        }
        contains();
    }

    public MathSet(Number[]... numbers) {
        int temp = getSizeOfDoubleArray(numbers);
        this.sizeOfArray = temp;
        this.numbers = new Number[sizeOfArray];
        currentLastIndex = 0;
        for (int i = 0; i < numbers.length; i++) {
            Number[] current = numbers[i];
            for (int j = 0; j < current.length; j++) {
                add(numbers[i][j]);
            }
        }
        contains();
    }

    public MathSet(MathSet ms) {
        this.sizeOfArray = ms.sizeOfArray;
        this.currentLastIndex = ms.currentLastIndex;
        this.numbers = new Number[sizeOfArray];
        for (int i = 0; i < ms.sizeOfArray; i++) {
            this.numbers[i] = ms.getNumbers()[i];
        }
        contains();
    }

    public MathSet(MathSet... ms) {
        int temp = 0;
        for (MathSet m : ms) {
            temp += getSizeOfDoubleArray(m.getNumbers());
        }
        this.sizeOfArray = temp;
        this.numbers = new Number[sizeOfArray];
        currentLastIndex = 0;
        for (MathSet m : ms) {
            join(m);
        }
        contains();
    }

    public Number[] getNumbers() {
        return numbers;
    }

    public void add(Number number) {
        if (sizeOfArray == currentLastIndex) {
            doubleSize(sizeOfArray);
        }
        this.numbers[currentLastIndex] = number;
        currentLastIndex++;
        contains();
    }

    public void add(Number... numbers) {
        for (Number number : numbers) {
            add(number);
        }
    }

    public void join(MathSet ms) {
        if (this.numbers == null) {
            new MathSet<>();
        } else {
            for (Number number : ms.getNumbers()) {
                add(number);
            }
        }
        contains();
    }

    public void join(MathSet... ms) {
        if (this.numbers == null) {
            new MathSet<>();
        } else {
            for (MathSet m : ms) {
                for (Number number : m.getNumbers()) {
                    add(number);
                }
            }
        }
    }

    public void intersection(MathSet ms) {
        Number[] newArr = new Number[currentLastIndex];
        int index = 0;
        for (int i = 0; i < currentLastIndex; i++) {
            for (int j = 0; j < ms.sizeOfArray; j++) {
                if (numbers[i].equals(ms.getNumbers()[j])) {
                    newArr[index] = numbers[i];
                    index++;
                }
            }
        }
        sizeOfArray = currentLastIndex;
        currentLastIndex = index;
        System.arraycopy(newArr, 0, numbers, 0, currentLastIndex);
        numbers = newArr;
    }

    public void intersection(MathSet... ms) {
        for (MathSet m : ms) {
            intersection(m);
        }
    }

    public void sortDesc() {
        Number temp;
        for (int i = 1; i < currentLastIndex + 1; i++) {
            for (int j = i; j > 0; j--) {
                if (numbers[j] != null) {
                    if (numbers[j].doubleValue() > numbers[j - 1].doubleValue()) {
                        temp = numbers[j];
                        numbers[j] = numbers[j - 1];
                        numbers[j - 1] = temp;
                    }
                }
            }
        }
    }

    public void sortDesc(int firstIndex, int lastIndex) {
        Number temp;
        for (int i = firstIndex; i < lastIndex; i++) {
            for (int j = i; j > firstIndex; j--) {
                if (numbers[j] != null) {
                    if (numbers[j].doubleValue() > numbers[j - 1].doubleValue()) {
                        temp = numbers[j];
                        numbers[j] = numbers[j - 1];
                        numbers[j - 1] = temp;
                    }
                }
            }
        }
    }

    public void sortDesc(Number value) {
        int index = -1;
        for (int i = 0; i < currentLastIndex; i++) { //???
            if (numbers[i].doubleValue() == value.doubleValue()) {
                index = i;
            }
        }
        sortDesc(index, currentLastIndex);
    }

    public void sortAsc() {
        Number temp;
        for (int i = 1; i < currentLastIndex + 1; i++) {
            for (int j = i; j > 0; j--) {
                if (numbers[j] != null) {
                    if (numbers[j].doubleValue() < numbers[j - 1].doubleValue()) {
                        temp = numbers[j];
                        numbers[j] = numbers[j - 1];
                        numbers[j - 1] = temp;
                    }
                }
            }
        }
    }

    public void sortAsc(int firstIndex, int lastIndex) {
        Number temp;
        for (int i = firstIndex; i < lastIndex + 1; i++) {
            for (int j = i; j > firstIndex; j--) {
                if (numbers[j] != null) {
                    if (numbers[j].doubleValue() < numbers[j - 1].doubleValue()) {
                        temp = numbers[j];
                        numbers[j] = numbers[j - 1];
                        numbers[j - 1] = temp;
                    }
                }
            }
        }
    }

    public void sortAsc(Number value) {
        int index = -1;
        for (int i = 0; i < currentLastIndex; i++) {
            if (numbers[i].equals(value)) {
                index = i;
            }
        }
        sortAsc(index, currentLastIndex);
    }

    public Number get(int index) {
        if (index <= currentLastIndex && index >= 0) {
            for (int i = 0; i < currentLastIndex; i++) {
                if (i == index) {
                    return numbers[i];
                }
            }
        }
        throw new IndexOutOfBoundsException("YOUR INDEX IS OUT OF BOUNDS");
    }

    public Number getMax() {
        Number max = Integer.MIN_VALUE;
        for (int i = 0; i < currentLastIndex; i++) {
            if (numbers[i].doubleValue() > max.doubleValue()) {
                max = numbers[i];
            }
        }
        return max;
    }

    public Number getMin() {
        Number min = Integer.MAX_VALUE;
        for (int i = 0; i < currentLastIndex; i++) {
            if (numbers[i].doubleValue() < min.doubleValue()) {
                min = numbers[i];
            }
        }
        return min;
    }

    public Number getAverage() {
        if (isEmpty()) {
            throw new NullPointerException("YOUR MATH_SET IS EMPTY");
        } else {
            Double sum = 0.0;
            for (int i = 0; i < currentLastIndex; i++) {
                sum += numbers[i].doubleValue();
            }
            return sum / currentLastIndex;
        }
    }

    public Number getMedian() {
        this.sortAsc();
        Number valueOfMediana = null;
        Integer mediana;
        if ((currentLastIndex + 1) % 2 == 1) {
            mediana = (currentLastIndex) / 2;
            valueOfMediana = numbers[mediana];
        } else {
            mediana = (currentLastIndex) / 2;
            valueOfMediana = (numbers[mediana].doubleValue() + numbers[mediana + 1].doubleValue()) / 2;
        }
        return valueOfMediana;
    }

    public void print(String s) {
        System.out.print("\nYOUR " + s + " MATH SET  ");
        System.out.println(Arrays.toString(toArray()));
        System.out.println();
    }

    public Number[] toArray() {
        Number[] newArr = new Number[currentLastIndex];
        for (int i = 0; i < currentLastIndex; i++) {
            newArr[i] = numbers[i];
        }
        return newArr;
    }

    public Number[] toArray(int firstIndex, int lastIndex) {
        Number[] newArr = new Number[lastIndex - firstIndex];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = numbers[i + firstIndex];
        }
        return newArr;
    }

    public MathSet<Number> cut(int firstIndex, int lastIndex) {
        MathSet<Number> mathSet = new MathSet(lastIndex - firstIndex);
        if (firstIndex >= 0 && firstIndex < lastIndex && lastIndex < currentLastIndex) {
            for (int i = 0; i < firstIndex; i++) {
                mathSet.add(numbers[i]);
            }
            for (int i = lastIndex + 1; i < currentLastIndex; i++) {
                mathSet.add(numbers[i]);
            }
            return mathSet;
        } else {
            throw new IndexOutOfBoundsException("INDEX OUT OF BONDS");
        }
    }

    public void clear() {
        Number[] cleared = new Number[INITIAL_SIZE];
        currentLastIndex = 0;
        sizeOfArray = INITIAL_SIZE;
        numbers = cleared;
    }

    public void clear(Number[] numbersi) {
        Number[] newArr = new Number[currentLastIndex + 1];
        int last = 0;
        for (int i = 0; i < currentLastIndex; i++) {
            boolean exists = false;
            for (int j = 0; j < numbersi.length; j++) {
                if (numbers[i].equals(numbersi[j])) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                for (int j = i + 1; j < currentLastIndex; j++)
                    numbers[j - 1] = numbers[j];
                currentLastIndex--;
                i--;
                if (currentLastIndex >= 0)
                    System.arraycopy(numbers, 0, newArr, 0, currentLastIndex);
                numbers = newArr;
            }
        }
    }

    private int getSizeOfDoubleArray(Number[]... numbers) {
        int temp = 0;
        for (int i = 0; i < numbers.length; i++) {
            Number[] current = numbers[i];
            for (int j = 0; j < current.length; j++) {
                temp++;
            }
        }
        return temp;
    }

    private void doubleSize(int capacity) {
        Number[] newArray = new Number[capacity * 2];
        System.arraycopy(this.numbers, 0, newArray, 0, this.sizeOfArray);
        this.numbers = newArray;
        this.sizeOfArray *= 2;
    }

    public void contains() {
        for (int i = 0; i < currentLastIndex; i++) {
            if(numbers[i]!=null){
                boolean exists = false;
                for (int j = 0; j < i; j++) {
                    if (numbers[j] != null) {
                        if (numbers[i].equals(numbers[j])) {
                            exists = true;
                            System.out.println("already exists");
                            break;
                        }
                    }
                }
                Number[] newArr = new Number[sizeOfArray];
                if (exists) {
                    for (int j = i + 1; j < currentLastIndex; j++)
                        numbers[j - 1] = numbers[j];
                    currentLastIndex--;
                    i--;
                    if (currentLastIndex >= 0) {
                        System.arraycopy(numbers, 0, newArr, 0, currentLastIndex);
                    }
                    numbers = newArr;
                }
            }
        }
    }

    private boolean isEmpty() {
        if (currentLastIndex == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "MathSet{" +
                "sizeOfArray=" + sizeOfArray +
                ", currentLastIndex=" + currentLastIndex +
                ", numbers=" + Arrays.toString(numbers) +
                '}';
    }

}

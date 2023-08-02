public class Test {
    public static void main(String[] args) {
        Number a = new Number(4);
        Number b = new Number(10);
        a.printValue();
        b.printValue();

    }
}

class Number {
    public int value = 0;

    public Number(int value) {
        this.value = value;
    }

    public void printValue() {
        System.out.println(value);
    }
}
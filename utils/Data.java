package utils;

public abstract class Data {

    public abstract boolean asBool();
    public abstract int asInt();
    public abstract double asDec();
    public abstract char asChar();
    public abstract String asStr();

    public static class Bool extends Data {
        private boolean value;
        public Bool(boolean value) {this.value = value;}

        @Override
        public boolean asBool() {
            return value;
        }
        @Override
        public int asInt() {
            return value? 1 : 0;
        }
        @Override
        public double asDec() {
            return value? 1. : 0.;
        }
        @Override
        public char asChar() {
            return value? '1' : '0';
        }
        @Override
        public String asStr() {
            return value? "true" : "false";
        }
    }

    public static class Int extends Data {
        private int value;
        public Int(int value) {this.value = value;}

        @Override
        public boolean asBool() {
            return value != 0;
        }
        @Override
        public int asInt() {
            return value;
        }
        @Override
        public double asDec() {
            return value;
        }
        @Override
        public char asChar() {
            return (char)value;
        }
        @Override
        public String asStr() {
            return ""+value;
        }
    }

    public static class Dec extends Data {
        private double value;
        public Dec(double value) {this.value = value;}

        @Override
        public boolean asBool() {
            return value != 0.;
        }
        @Override
        public int asInt() {
            return (int)value;
        }
        @Override
        public double asDec() {
            return value;
        }
        @Override
        public char asChar() {
            return (char)value;
        }
        @Override
        public String asStr() {
            return ""+value;
        }
    }

    public static class Char extends Data {
        private char value;
        public Char(char value) {this.value = value;}

        @Override
        public boolean asBool() {
            return value != '0';
        }
        @Override
        public int asInt() {
            return value;
        }
        @Override
        public double asDec() {
            return value;
        }
        @Override
        public char asChar() {
            return value;
        }
        @Override
        public String asStr() {
            return ""+value;
        }
    }

    public static class Str extends Data {
        private String value;
        public Str(String value) {this.value = value;}

        @Override
        public boolean asBool() {
            return value.length() > 0;
        }
        @Override
        public int asInt() {
            return value.length();
        }
        @Override
        public double asDec() {
            return value.length();
        }
        @Override
        public char asChar() {
            return (value.length()>0)? value.charAt(0) : '\0';
        }
        @Override
        public String asStr() {
            return value;
        }
    }
}

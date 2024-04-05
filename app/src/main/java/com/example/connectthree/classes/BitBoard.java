package com.example.connectthree.classes;

public class BitBoard {
    String bitBoard;

    public BitBoard(String bitBoard) {
        this.bitBoard = bitBoard;
    }

    public boolean check(){
        String check;
        String diagonal1;
        String horizontal;
        String diagonal2;
        String vertical;

        check = bitwiseAnd(this.toString(), shiftStringRight(this.toString(), 5));
        diagonal1 = bitwiseAnd(check, shiftStringRight(this.toString(), 2*5));
        if (diagonal1.contains("1")) { // check \ diagonal
            System.out.println("Diagonal 1 check");
            return true;
        }
        check = bitwiseAnd(this.toString(), shiftStringRight(this.toString(), 6));
        horizontal = bitwiseAnd(check, shiftStringRight(this.toString(), 2*6));
        if(horizontal.contains("1")){
            System.out.println("Horizontal Check");
            return true;
        }
        check = bitwiseAnd(this.toString(), shiftStringRight(this.toString(), 7));
        diagonal2 = bitwiseAnd(check, shiftStringRight(this.toString(), 2*7));
        if (diagonal2.contains("1")) { // check / diagonal
            System.out.println("Diagonal 2 check");
            return true;
        }
        check = bitwiseAnd(this.toString(), shiftStringRight(this.toString(), 1));
        vertical = bitwiseAnd(check, shiftStringRight(this.toString(), 2));
        if(vertical.contains("1")){
            System.out.println("Vertical Check");
            return true;
        }

        return false;
    }

    public static String shiftStringLeft(String str, int shiftAmount) {
        int length = str.length();
        shiftAmount = shiftAmount % length;
        return str.substring(shiftAmount) + str.substring(0, shiftAmount);
    }

    public static String shiftStringRight(String str, int shiftAmount) {
        int length = str.length();
        shiftAmount = shiftAmount % length;
        return str.substring(length - shiftAmount) + str.substring(0, length - shiftAmount);
    }

    public static String bitwiseAnd(String binary1, String binary2) {
        int length = Math.min(binary1.length(), binary2.length());
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char char1 = binary1.charAt(i);
            char char2 = binary2.charAt(i);
            if (char1 == '1' && char2 == '1') {
                result.append('1');
            } else {
                result.append('0');
            }
        }
        return result.toString();
    }

    @Override
    public String toString() {
        return bitBoard;
    }
}

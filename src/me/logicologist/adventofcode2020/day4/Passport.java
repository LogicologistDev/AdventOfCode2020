package me.logicologist.adventofcode2020.day4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Passport {

    private final String byr, iyr, eyr, hgt, hcl, ecl, pid;

    public Passport(String byr, String iyr, String eyr, String hgt, String hcl, String ecl, String pid) {
        this.byr = byr;
        this.iyr = iyr;
        this.eyr = eyr;
        this.hgt = hgt;
        this.hcl = hcl;
        this.ecl = ecl;
        this.pid = pid;
    }

    public boolean isValid() {
        try {
            if (Integer.parseInt(this.byr) < 1920 || Integer.parseInt(this.byr) > 2002) return false;
            if (Integer.parseInt(this.iyr) < 2010 || Integer.parseInt(this.iyr) > 2020) return false;
            if (Integer.parseInt(this.eyr) < 2020 || Integer.parseInt(this.eyr) > 2030) return false;
            if (!this.hgt.contains("cm") && !this.hgt.contains("in")) return false;
            if (this.hgt.contains("cm")) {
                if (Integer.parseInt(this.hgt.replace("cm", "")) < 150 || Integer.parseInt(this.hgt.replace("cm", "")) > 193) return false;
            } else {
                if (Integer.parseInt(this.hgt.replace("in", "")) < 59 || Integer.parseInt(this.hgt.replace("in", "")) > 76) return false;
            }
            Pattern pattern = Pattern.compile("#[0123456789abcdef][0123456789abcdef][0123456789abcdef][0123456789abcdef][0123456789abcdef][0123456789abcdef]");
            if (this.hcl.length() < 7) return false;
            if (!pattern.matcher(this.hcl).matches()) return false;
            List<String> validEcl = new ArrayList<>(Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth"));
            if (!validEcl.contains(this.ecl)) return false;
            Pattern pattern1 = Pattern.compile("[0123456789][0123456789][0123456789][0123456789][0123456789][0123456789][0123456789][0123456789][0123456789]");
            return pattern1.matcher(this.pid).matches();
        } catch (Exception ex) {
            return false;
        }
    }
}

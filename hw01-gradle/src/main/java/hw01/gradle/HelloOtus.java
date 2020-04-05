package hw01.gradle;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;

public class HelloOtus {
    public String getGreeting() {
        return "Hello Otus.";
    }

    public static void main(String[] args) {
        System.out.println(new HelloOtus().getGreeting());
        HashFunction hf = Hashing.murmur3_32();
        HashCode hc = hf.newHasher().putString("MurMur", Charsets.UTF_8).hash();
        System.out.println("MurMur: " + hc);
    }
}

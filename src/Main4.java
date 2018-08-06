import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

public class Main4 {
	private static int mod = 1_000_000_007;

	public static void main(String[] args){
		FastScanner sc = new FastScanner();
		char[] S = sc.next().toCharArray();
		int len = S.length;
		long aPettern= 0;
		long abPettern = 0;
		long abcPettern = 0;
		long multiPet = 1;
		for(int i=0;i<len;i++){
			switch (S[i]) {
			//Aは?での分岐パターンのみ追加
				case 'A': aPettern += multiPet; break;
				//BはAのパターンの数だけ追加
				case 'B': abPettern += aPettern; break;
				//CはABのパターンの数だけ追加
				case 'C': abcPettern += abPettern; break;
				/**
				 * abcが既に存在しているとした場合、abcPetternを3倍(abcのいずれかを選択)、
				 * ?をCとする場合を考慮して、abcPetternにabPetternを追加
				 *
				 * 同様にabが既に存在するとした場合、abPetternを3倍、
				 * abPetternにaPetternを追加
				 */
				case '?':{
					abcPettern = abcPettern * 3 + abPettern;
					abPettern = abPettern * 3 + aPettern;
					aPettern = aPettern * 3 + multiPet;
					multiPet *= 3;
					break;
				}
			}
			multiPet %= mod;
			aPettern %= mod;
			abPettern %= mod;
			abcPettern %= mod;
		}
		System.out.println(abcPettern);
	}
}

class FastScanner {
    private final InputStream in = System.in;
    private final byte[] buffer = new byte[1024];
    private int ptr = 0;
    private int buflen = 0;
    private boolean hasNextByte() {
        if (ptr < buflen) {
            return true;
        }else{
            ptr = 0;
            try {
                buflen = in.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (buflen <= 0) {
                return false;
            }
        }
        return true;
    }
    private int readByte() { if (hasNextByte()) return buffer[ptr++]; else return -1;}
    private static boolean isPrintableChar(int c) { return 33 <= c && c <= 126;}
    public boolean hasNext() { while(hasNextByte() && !isPrintableChar(buffer[ptr])) ptr++; return hasNextByte();}
    public String next() {
        if (!hasNext()) throw new NoSuchElementException();
        StringBuilder sb = new StringBuilder();
        int b = readByte();
        while(isPrintableChar(b)) {
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }
    public long nextLong() {
        if (!hasNext()) throw new NoSuchElementException();
        long n = 0;
        boolean minus = false;
        int b = readByte();
        if (b == '-') {
            minus = true;
            b = readByte();
        }
        if (b < '0' || '9' < b) {
            throw new NumberFormatException();
        }
        while(true){
            if ('0' <= b && b <= '9') {
                n *= 10;
                n += b - '0';
            }else if(b == -1 || !isPrintableChar(b)){
                return minus ? -n : n;
            }else{
                throw new NumberFormatException();
            }
            b = readByte();
        }
    }
    public int nextInt() {
        long nl = nextLong();
        if (nl < Integer.MIN_VALUE || nl > Integer.MAX_VALUE) throw new NumberFormatException();
        return (int) nl;
    }
    public double nextDouble() { return Double.parseDouble(next());}
}

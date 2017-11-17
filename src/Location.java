import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Location {
    static ConcurrentHashMap<String,Location> cache;
    int r,c;
    static{
        cache = new ConcurrentHashMap<>();
    }
    private Location(int r, int c){
        this.r = r;
        this.c = c;
        cache.put(r+","+c,this);
    }
    public static Location get(int r, int c){
        if(cache.containsKey(r+","+c)){
            return cache.get(r+","+c);
        }
        return new Location(r,c);
    }

    public Location add(int deltaR, int deltaC){
        return get(r + deltaR, c + deltaC);
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return (r+","+c).hashCode();
    }
}

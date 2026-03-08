package edu.masanz.da.kk.model;

import edu.masanz.da.kk.utils.Security;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Kuki {

    private final static long TIEMPO_PARA_VIVIR = 5 * 24 * 60 * 60 * 1000;//TTL en ms para las sesiones (5 días)

    private String id;
    private long fecha;
    private long vida;

    public Kuki(String id, long fecha, long vida) {
        this.id = id;
        this.fecha = fecha;
        this.vida = vida;
    }

    public Kuki(String id, String fecha, long vida) {
        this.id = id;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        try {
            Date date = sdf.parse(fecha);
            this.fecha = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.vida = vida;
    }

    public Kuki(String id) {
        this.id = id;
        this.fecha = System.currentTimeMillis();
        this.vida = TIEMPO_PARA_VIVIR;
    }

    public Kuki() {
        this(   Security.generateRandomId(),
                System.currentTimeMillis(),
                TIEMPO_PARA_VIVIR               );
    }

    public static Kuki fromCsv(String csv) {
        String[] parts = csv.split(",");
        return new Kuki(parts[0], Long.parseLong(parts[1]), Long.parseLong(parts[2]));
    }

    // region Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public long getVida() {
        return vida;
    }

    public void setVida(long vida) {
        this.vida = vida;
    }

    // endregion

    public String toCsv() {
        return id + "," + fecha + "," + vida;
    }

    @Override
    public String toString() {
        return toCsv();
    }

    public static void main(String[] args) {
        String[] ids = {
                "d519dca360045364abdb0b38f381f57bb1173106",
                "85aee931a65b7296d69494cee0964349b727b4a7",
                "026772e33b50bad6fd10bd6cc6a70ebd6699561b",
                "2c895c6a10f7b437cb5e329e57acb28c7ffd1e52",
                "7eaa4cfe2109e860e17c5f4dcbb061fa49836855",
        };
        String[] its = {
                "2025/02/21 11:33:12.345",
                "2025/02/23 09:15:14.034",
                "2025/02/25 07:11:45.143",
                "2025/02/24 23:07:43.564",
                "2025/02/22 06:37:56.221",
        };
        for (int i = 0; i < 5; i++) {
            Kuki s = new Kuki(ids[i], its[i], TIEMPO_PARA_VIVIR);
            System.out.println(s);
        }
    }

}

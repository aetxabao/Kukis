package edu.masanz.da.kk.model;

public class Usuario {
    private String id;
    private String nombre;
    private String perfil;
    private String idSesion;

    public Usuario(String id, String nombre, String perfil, String idSesion) {
        this.id = id;
        this.nombre = nombre;
        this.perfil = perfil;
        this.idSesion = idSesion;
    }

    public Usuario() {
        this("","", "", "");
    }

    public static Usuario fromCsv(String csv) {
        String[] partes = csv.split(",");
        return new Usuario(partes[0], partes[1], partes[2], partes[3]);
    }

    // region Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(String idSesion) {
        this.idSesion = idSesion;
    }
    // endregion

    public String toJson() {
        return "{"
                + "\"id\":\"" + id + "\","
                + "\"nombre\":\"" + nombre + "\","
                + "\"rol\":\"" + perfil + "\","
                + "\"idSesion\":\"" + idSesion + "\""
                + "}";
    }

    public String toCsv() {
        return id + "," + nombre + "," + perfil + "," + idSesion;
    }

    @Override
    public String toString() {
        return toCsv();
    }

    public static void main(String[] args) {
        String[] ids = {"aetxabao", "jsotera", "atortoslop", "murizmata", "scastilsag"};
        String[] nombres = {"Aitor", "Javi", "Alba", "Mikel", "Silvia"};
        String[] perfiles = {"NOR", "NOR", "VIP", "NOR", "VIP"};
        String[] sesiones = {
                "d519dca360045364abdb0b38f381f57bb1173106",
                "85aee931a65b7296d69494cee0964349b727b4a7",
                "026772e33b50bad6fd10bd6cc6a70ebd6699561b",
                "2c895c6a10f7b437cb5e329e57acb28c7ffd1e52",
                "7eaa4cfe2109e860e17c5f4dcbb061fa49836855",
        };
        for (int i = 0; i < ids.length; i++) {
            Usuario u = new Usuario(ids[i], nombres[i], perfiles[i], sesiones[i]);
            System.out.println(u);
        }
    }

}

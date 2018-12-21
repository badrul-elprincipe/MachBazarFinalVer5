package com.badrul.user.machbazarfinalver5;

class memoitem {

    String id;
    String naam;
    String koyta;
    String koto;



    public memoitem(){

    }
    memoitem(String id, String naam, String koyta, String koto){
        this.id=id;
        this.naam=naam;

        this.koyta=koyta;
        this.koto=koto;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getKoyta() {
        return koyta;
    }

    public void setKoyta(String koyta) {
        this.koyta = koyta;
    }

    public String getKoto() {
        return koto;
    }

    public void setKoto(String koto) {
        this.koto = koto;
    }
}

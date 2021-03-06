package amgapp;

class VertretungModel {

    private final String Stunde;
    private final String Klasse;
    private final String Art;
    private final String Fach;
    private final String ErsatzFach;
    private final String Vertretungslehrer;
    private final String Raum;
    private final String Hinweise;

    VertretungModel(String Stunde, String Klasse, String Art, String Fach, String ErsatzFach, String Vertretungslehrer, String Raum, String Hinweise){
        this.Stunde = Stunde;
        this.Klasse = Klasse;
        this.Art = Art.replaceAll("Stunde f.llt aus","Stunde f&auml;llt aus").replaceAll("Raum-.nd.","Raum-&Auml;nd.");
        this.Fach = Fach;
        this.ErsatzFach = ErsatzFach;
        this.Vertretungslehrer = Vertretungslehrer;
        this.Raum = Raum;
        this.Hinweise = Hinweise;
    }

    String getStunde(){
        return Stunde;
    }

    String getKlasse(){
        return Klasse;
    }

    String getArt(){
        return Art;
    }

    String getFach(){
        return Fach;
    }

    String getErsatzFach(){
        return ErsatzFach;
    }

    String getVertretungslehrer(){
        return Vertretungslehrer;
    }

    String getRaum(){
        return Raum;
    }

    String getHinweise(){
        return Hinweise;
    }

    public String toString(){
        return "Stunde: "+Stunde+"; Klasse: "+Klasse+"; Art: "+Art+"; Fach: "+Fach+"; Ersatzfach: "+ErsatzFach+"; Vertretungslehrer: "+Vertretungslehrer+"; Raum: "+Raum+"; Hinweise: "+Hinweise;
    }

}

package www.amg_witten.de.apptest;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ITTeamHolen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    MenuItem offen;
    MenuItem inBearbeitung;
    MenuItem fertig;
    MenuItem gebH;
    MenuItem gebA;
    MenuItem gebN;
    MenuItem et2;
    MenuItem et1;
    MenuItem et0;
    MenuItem etZ;
    MenuItem etU;
    MenuItem raum01;
    MenuItem raum02;
    MenuItem raum03;
    MenuItem raum04;
    MenuItem raum05;
    MenuItem raum06;
    MenuItem raum07;
    MenuItem raum08;
    MenuItem raum09;
    MenuItem raum10;
    MenuItem raum11;
    MenuItem raum12;
    MenuItem raum13;
    MenuItem raum14;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.it_team_holen_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Methoden methoden = new Methoden();
        methoden.onCreateFillIn(this,this,2);

        ITTeamHolenAnzeigen("select * from fehlermeldungen where status=\"Offen\";");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_filter_it_team_holen, menu);

        offen=menu.findItem(R.id.Offen);
        inBearbeitung=menu.findItem(R.id.InBearbeitung);
        fertig=menu.findItem(R.id.Fertig);
        gebH=menu.findItem(R.id.FilterGebaeudeH);
        gebA=menu.findItem(R.id.FilterGebaeudeA);
        gebN=menu.findItem(R.id.FilterGebaeudeN);
        et2=menu.findItem(R.id.FilterEtage2);
        et1=menu.findItem(R.id.FilterEtage1);
        et0=menu.findItem(R.id.FilterEtage0);
        etZ=menu.findItem(R.id.FilterEtageZ);
        etU=menu.findItem(R.id.FilterEtageU);
        raum01=menu.findItem(R.id.FilterRaum01);
        raum02=menu.findItem(R.id.FilterRaum02);
        raum03=menu.findItem(R.id.FilterRaum03);
        raum04=menu.findItem(R.id.FilterRaum04);
        raum05=menu.findItem(R.id.FilterRaum05);
        raum06=menu.findItem(R.id.FilterRaum06);
        raum07=menu.findItem(R.id.FilterRaum07);
        raum08=menu.findItem(R.id.FilterRaum08);
        raum09=menu.findItem(R.id.FilterRaum09);
        raum10=menu.findItem(R.id.FilterRaum10);
        raum11=menu.findItem(R.id.FilterRaum11);
        raum12=menu.findItem(R.id.FilterRaum12);
        raum13=menu.findItem(R.id.FilterRaum13);
        raum14=menu.findItem(R.id.FilterRaum14);

        offen.setChecked(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.FilterEtage:
                return true;
            case R.id.FilterGebaeude:
                return true;
            case R.id.FilterStatus:
                return true;
            case R.id.FilterRaum:
                return true;

            default:
                if(item.isChecked()){
                    item.setChecked(false);
                    ITTeamHolenAnzeigen(AnfrageGenerieren());
                }
                else {
                    item.setChecked(true);
                    ITTeamHolenAnzeigen(AnfrageGenerieren());
                }
                return true;
        }
    }

    public String AnfrageGenerieren() {
        String anfrage = "select * from fehlermeldungen where ";
        boolean etwMarkiert = false;

        if(offen.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="status=\"Offen\"";
        }
        if(inBearbeitung.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="status=\"In Bearbeitung\"";
        }
        if(fertig.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="status=\"Fertig\"";
        }
        if(gebH.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="gebaeude=\"H\"";
        }
        if(gebA.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="gebaeude=\"A\"";
        }
        if(gebN.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="gebaeude=\"N\"";
        }
        if(et2.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="etage=\"2\"";
        }
        if(et1.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="etage=\"1\"";
        }
        if(et0.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="etage=\"0\"";
        }
        if(etZ.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="etage=\"Z\"";
        }
        if(etU.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="etage=\"U\"";
        }
        if(raum01.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="raum=\"01\"";
        }
        if(raum02.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="raum=\"02\"";
        }
        if(raum03.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="raum=\"03\"";
        }
        if(raum04.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="raum=\"04\"";
        }
        if(raum05.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="raum=\"05\"";
        }
        if(raum06.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="raum=\"06\"";
        }
        if(raum07.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="raum=\"07\"";
        }
        if(raum08.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="raum=\"08\"";
        }
        if(raum09.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="raum=\"09\"";
        }
        if(raum10.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="raum=\"10\"";
        }
        if(raum11.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="raum=\"11\"";
        }
        if(raum12.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="raum=\"12\"";
        }
        if(raum13.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="raum=\"13\"";
        }
        if(raum14.isChecked()){
            if(etwMarkiert){
                anfrage+=" OR ";
            }
            etwMarkiert=true;
            anfrage+="raum=\"14\"";
        }

        anfrage+=";";

        return anfrage;
    }

    public void ITTeamHolenAnzeigen(final String filter){
        final Activity ac=this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket();
                    s.connect(new InetSocketAddress(Startseite.ip,Startseite.port),5000);
                    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    PrintWriter pw = new PrintWriter(s.getOutputStream());

                    pw.println("ITTeamHolen");
                    pw.flush();
                    pw.println(filter);
                    pw.flush();
                    int eintraegeZahl = Integer.parseInt(br.readLine());

                    final ViewGroup vg = (ViewGroup)findViewById(R.id.content_itteam_holen);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            vg.removeAllViews();
                        }
                    });
                    final String[] texte = new String[eintraegeZahl];
                    for(int i=0;i<eintraegeZahl;i++){
                        String readLine=br.readLine();
                        texte[i]=readLine;
                        readLine = readLine.replaceAll("//","\n");
                        readLine = readLine.replaceAll("ae","ä");
                        System.out.println(texte[i]);
                        String[] result = texte[i].split("Status: ");
                        final String status = result[1];

                        final View line = new View(ac);
                        line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,2));
                        line.setBackgroundColor(0xff888888);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                vg.addView(line);
                            }
                        });

                        final RelativeLayout rl = new RelativeLayout(ac);
                        TextView tv = new TextView(ac);
                        Button button = new Button(ac);

                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(10,10,0,10);
                        tv.setLayoutParams(params);
                        tv.setText(readLine);
                        rl.addView(tv);
                        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
                        params.setMargins(0,25,10,0);
                        button.setText("Status");
                        switch (status){
                            case "Offen":
                                button.setTextColor(0xffff0000);
                                break;
                            case "In Bearbeitung":
                                button.setTextColor(0xffFFBF00);
                                break;
                            case "Fertig":
                                button.setTextColor(0xff00ff00);
                                break;
                        }
                        final int finali = i;
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String[] grpname = {"Offen","In Bearbeitung","Fertig"};

                                LinearLayout ll = new LinearLayout(ac);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                ll.setLayoutParams(lp);
                                ll.setOrientation(LinearLayout.VERTICAL);
                                RadioButton rd1 = new RadioButton(ac);
                                RadioButton rd2 = new RadioButton(ac);
                                RadioButton rd3 = new RadioButton(ac);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(ac);

                                rd1.setText(grpname[0]);
                                rd2.setText(grpname[1]);
                                rd3.setText(grpname[2]);

                                switch (status){
                                    case "Offen":
                                        rd1.setChecked(true);
                                        break;
                                    case "In Bearbeitung":
                                        rd2.setChecked(true);
                                        break;
                                    case "Fertig":
                                        rd3.setChecked(true);
                                        break;
                                }

                                ll.addView(new TextView(ac));
                                ll.addView(rd1);
                                ll.addView(rd2);
                                ll.addView(rd3);

                                dialog.setView(ll);
                                dialog.setTitle("Bitte wähle den Status aus!");
                                dialog.setCancelable(true);

                                final Dialog diag = dialog.create();
                                dialog.show();

                                rd1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        StatusAendern(grpname[0],texte[finali]);
                                        diag.dismiss();
                                    }
                                });
                                rd2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        StatusAendern(grpname[1],texte[finali]);
                                        diag.dismiss();
                                    }
                                });
                                rd3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        StatusAendern(grpname[2],texte[finali]);
                                        diag.dismiss();
                                    }
                                });

                            }
                        });
                        button.setTextSize(12);
                        button.setLayoutParams(params);
                        rl.addView(button);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                vg.addView(rl);
                            }
                        });

                    }
                    if(eintraegeZahl==0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tv = new TextView(ac);
                                tv.setText("Keine Einträge vorhanden");
                                vg.addView(tv);
                            }
                        });
                    }
                } catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ac,"Fehler beim Verbinden zum Server",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ac, Startseite.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(this, Startseite.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_startseite) {
            Intent intent = new Intent(this, Startseite.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.nav_it_team_senden) {
            startActivity(new Intent(this,ITTeamSenden.class));
        } else if (id == R.id.nav_login) {
            startActivity(new Intent(this,Login.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void StatusAendern(final String neu, String text){
        final Activity ac = this;

        System.out.println(neu);

        final String[] daten = new String[4];
        String[] results = text.split("//");
        daten[0]=results[0].replace("Datum: ","");
        daten[1]=results[2].replace("Gebäude: ","");
        daten[2]=results[3].replace("Etage: ","");
        daten[3]=results[4].replace("Raum: ","");
        daten[4]=results[5].replace("Wichtigkeit: ","");
        daten[5]=results[6].replace("Fehler: ","");
        daten[6]=results[7].replace("Beschreibung: ","");
        daten[7]=results[8].replace("Status: ","");


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket();
                    s.connect(new InetSocketAddress(Startseite.ip,Startseite.port),5000);
                    PrintWriter pw = new PrintWriter(s.getOutputStream());

                    pw.println("ITTeamLoeschen");
                    pw.flush();
                    pw.println("delete from fehlermeldungen where gebaeude=\""+daten[1]+" and etage=\""+daten[2]+" and raum=\""+daten[3]+"\" and fehler=\""+daten[5]+"\";");
                    pw.flush();

                    s.close();

                    s=new Socket();
                    s.connect(new InetSocketAddress(Startseite.ip,Startseite.port),5000);
                    pw=new PrintWriter(s.getOutputStream());

                    pw.println("ITTeamMelden");
                    pw.flush();
                    pw.println(daten[0]);
                    pw.flush();
                    pw.println(daten[1]);
                    pw.flush();
                    pw.println(daten[2]);
                    pw.flush();
                    pw.println(daten[3]);
                    pw.flush();
                    pw.println(daten[4]);
                    pw.flush();
                    pw.println(daten[5]);
                    pw.flush();
                    pw.println(daten[6]);
                    pw.flush();
                    pw.println(daten[7]);
                    pw.flush();
                    pw.close();

                    startActivity(new Intent(ac,ITTeamHolen.class));

                } catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ac,"Fehler beim Ändern des Status",Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent = new Intent(ac, Startseite.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        }).start();
    }

}
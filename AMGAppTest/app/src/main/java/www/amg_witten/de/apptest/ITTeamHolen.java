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

        offen.setChecked(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.Offen:
                if(item.isChecked()){
                    item.setChecked(false);
                    ITTeamHolenAnzeigen(AnfrageGenerieren());
                }
                else {
                    item.setChecked(true);
                    ITTeamHolenAnzeigen(AnfrageGenerieren());
                }
                return true;
            case R.id.InBearbeitung:
                if(item.isChecked()){
                    item.setChecked(false);
                    ITTeamHolenAnzeigen(AnfrageGenerieren());
                }
                else {
                    item.setChecked(true);
                    ITTeamHolenAnzeigen(AnfrageGenerieren());
                }
                return true;
            case R.id.Fertig:
                if(item.isChecked()){
                    item.setChecked(false);
                    ITTeamHolenAnzeigen(AnfrageGenerieren());
                }
                else {
                    item.setChecked(true);
                    ITTeamHolenAnzeigen(AnfrageGenerieren());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String AnfrageGenerieren() {
        String anfrage = "select * from fehlermeldungen where ";
        boolean etwMarkiert = false;

        System.out.println(offen);
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
        daten[0]=results[1].replace("Raum: ","");
        daten[1]=results[2].replace("Wichtigkeit: ","");
        daten[2]=results[3].replace("Fehler: ","");
        daten[3]=results[4].replace("Beschreibung: ","");


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket();
                    s.connect(new InetSocketAddress(Startseite.ip,Startseite.port),5000);
                    PrintWriter pw = new PrintWriter(s.getOutputStream());

                    pw.println("ITTeamLoeschen");
                    pw.flush();
                    pw.println("delete from fehlermeldungen where raum=\""+daten[0]+"\" and fehler=\""+daten[2]+"\";");
                    pw.flush();

                    s.close();

                    s=new Socket();
                    s.connect(new InetSocketAddress(Startseite.ip,Startseite.port),5000);
                    pw=new PrintWriter(s.getOutputStream());

                    pw.println("ITTeamMelden");
                    pw.flush();
                    pw.println(daten[0]);
                    pw.flush();
                    System.out.println("SENT 1");
                    pw.println(daten[1]);
                    pw.flush();
                    System.out.println("SENT 2");
                    pw.println(daten[2]);
                    pw.flush();
                    System.out.println("SENT 3");
                    pw.println(daten[3]);
                    pw.flush();
                    System.out.println("SENT 4");
                    pw.println(neu);
                    pw.flush();
                    System.out.println("SENT 5");

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

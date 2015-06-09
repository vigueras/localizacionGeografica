package com.example.guadalupe.localizaciongeografica;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

import java.util.Vector;


public class Principal extends Activity {

    private Button  btnActualizar, btnDesactivar;
    private TextView lblLatitud, lblLongitud, lblPrecision,lblEstadoProveedor;
  private LocationManager locManager;
    private  LocationListener locListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btnActualizar = (Button) findViewById(R.id.BtnActualizar);
        btnDesactivar = (Button) findViewById(R.id.BtnDesactivar);
        lblLatitud = (TextView) findViewById(R.id.LblPosLatitud);
        lblLongitud = (TextView) findViewById(R.id.LblPosLongitud);
        lblPrecision = (TextView) findViewById(R.id.LblPosPrecision);
        lblEstadoProveedor = (TextView) findViewById(R.id.LblEstado);



        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comenzarLocalizacion();

            }
        });
        btnDesactivar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                locManager.removeUpdates(locListener);

            }
        });
    }





            private void comenzarLocalizacion()

    {
        //Obtenemos una referencia al LocationManager
        locManager =
                (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //Mostramos la ultima posocion conocida
        Location location=
                locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mostrarPosicion(location);

        //Nos registramos para recibir actualizaciones de la posicion
        locListener= new LocationListener() {
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }

            public void onProviderDisabled(String provider) {
                lblEstadoProveedor.setText("Provider OFF");
            }

            public void onProviderEnabled(String provider) {
                lblEstadoProveedor.setText("Provider ON");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                lblEstadoProveedor.setText("Provider Status:" + status);

            }
        };
            locManager.requestLocationUpdates(
                    locManager.GPS_PROVIDER,15000,0,locListener);
        }

    private void mostrarPosicion (Location loc) {
        if (loc != null) {
            lblLatitud.setText("Latitud:" + String.valueOf(loc.getLatitude()));
            lblLongitud.setText("Longitud:" + String.valueOf(loc.getLongitude()));
            lblPrecision.setText("Precision:" + String.valueOf(loc.getAccuracy()));
            Log.i("LocAndroid", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        }
        else
        {

            lblLatitud.setText("Latitud: (sin_datos)");
            lblLongitud.setText("Longitud: (sin_datos)");
            lblPrecision.setText("Precision: (sin_datos)");
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }


}

package com.emproducciones.verduleriadelpueblo.impresion;

import android.graphics.Color;
import android.util.DisplayMetrics;
import android.widget.*;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.*;
import com.dantsu.escposprinter.exceptions.*;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.emproducciones.verduleriadelpueblo.MainApplication;
import com.emproducciones.verduleriadelpueblo.R;
import com.emproducciones.verduleriadelpueblo.modelo.constantes;
import com.emproducciones.verduleriadelpueblo.modelo.vtaProd;
import java.text.SimpleDateFormat;
import java.util.*;

public class impresion {

    public static final String NO_SE_ENCUENTRA_BLUETOOTH = "No se encuentra Bluetooth";
    public static final String MODELO_IMPRESORA = "MJ5808";
    public static final String BLUETOOTH_DEVICE_FOUND = "Bluetooth device found.";
    public static final String BLUETOOTH_CONECTADO = "Bluetooth Conectado";
    private String forFecha = "dd-MM-yyyy";
    private String forHs = "HH:mm";
    private String fecha="";
    private String hs="";
    private Double total = 0.0;
    private BluetoothConnection bluetoothConnection;


    public void conectarBluetooh(Button v, BluetoothConnection impresoraCon){
        v.setTextColor(Color.GREEN);
        v.setText(BLUETOOTH_CONECTADO);
        bluetoothConnection = impresoraCon;
       /* BluetoothPrintersConnections printers = new BluetoothPrintersConnections();
        BluetoothConnection[] bluetoothPrinters = printers.getList();

        if (bluetoothPrinters != null && bluetoothPrinters.length > 0) {
            for (BluetoothConnection printer : bluetoothPrinters) {
                if (printer.getDevice().getName().equals(MODELO_IMPRESORA))
                    try {
                        bluetoothConnection = printer.connect();
                        v.setTextColor(Color.GREEN);
                        v.setText(BLUETOOTH_CONECTADO);
                    } catch (EscPosConnectionException e) {
                        e.printStackTrace();
                    }
            }
            if (bluetoothConnection==null){
                v.setTextColor(Color.RED);
                v.setText(NO_SE_ENCUENTRA_BLUETOOTH);
            }
        }*/
    }

    public void imprimirTicket(ArrayList<vtaProd> produ) throws EscPosConnectionException, EscPosParserException, EscPosEncodingException, EscPosBarcodeException {
        verHoras();
        EscPosPrinter printer = new EscPosPrinter(bluetoothConnection, 203, 48f, 32);
        printer
                .printFormattedText(
                        "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer,
                                MainApplication.Companion.applicationContext().
                                        getResources().
                                        getDrawableForDensity(R.drawable.encabezado, DisplayMetrics.DENSITY_MEDIUM))+"</img>\n" +
                                "[L]\n" +
                                "[R]<font size='normal'>" + fecha + " " + hs +"</font>\n" +
                                "[L]\n" +
                                "[L]<b>Cant.[L]Nombre[R]Precio\n" +
                                "[C]================================\n" +
                                "[L]\n" + procesarVtaTicket(produ)+
                                "[L]\n" +
                                "[C]--------------------------------\n" +
                                "[R]TOTAL :[R]" +"$ " + String.format("%.2f",total) + "\n" +
                                "[L]\n" +
                                "[C]-MUCHAS GRACIAS POR SU COMPRA!-\n"
                        );
    }

    private void verHoras(){
        SimpleDateFormat forFecha = new SimpleDateFormat(this.forFecha);
        SimpleDateFormat forHs = new SimpleDateFormat(this.forHs);
        Date fifi = new Date();
        fecha = forFecha.format(fifi);
        hs = forHs.format(fifi);
    }

    private String procesarVtaTicket(ArrayList<vtaProd> produ){
        String mensaje="";
        String uniVta = "Kg";
        String cantidad="";
        String nombre="";
        int caracteres=0;

        for (int i = 0;i<produ.size();i++){

            if (produ.get(i).getProdu().getUniVenta().equals(constantes.constantes.uni)){
                uniVta= "U.";
                cantidad =  ((String.valueOf(produ.get(i).getCantidad())).
                            replace(".","")).replace("0","");
            }else cantidad =  String.format("%.3f",produ.get(i).getCantidad());
            
            if (produ.get(i).getProdu().getNombre().length()<10){
                nombre = produ.get(i).getProdu().getNombre();
            }else {
                nombre = produ.get(i).getProdu().getNombre().substring(0,10) + ".";
            }

            mensaje += "[L]<b>"+ cantidad + " " + uniVta + "<b>[L]"
                    + nombre +"[R] $" + String.format("%.2f",produ.get(i).getTotal()) +"\n";
            total += produ.get(i).getTotal();
            uniVta = "Kg";
        }
        return mensaje;
    }

    public void closeBT() {
        if (bluetoothConnection!=null) if (bluetoothConnection.isConnected()) bluetoothConnection.disconnect();
    }
}

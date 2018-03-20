package xyz.somniumproject.printertest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.imagpay.Settings
import com.imagpay.ttl.TTLHandler
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var app: MyApplication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val process = Runtime.getRuntime().exec("su")
            process.waitFor()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val handler = TTLHandler(this)
        val settings = Settings(handler)
        handler.setParameters("/dev/ttyHSL1", 115200)

        handler.isShowLog = true
        println("ONLINE:" + handler.isPowerOn)
        app = application as MyApplication
        app._handler = handler
        app._setting = settings

        try {
            if (handler.isConnected) {
                sendMessage("Connect Res:" + handler.connect())
            } else {
                handler.close()
                sendMessage("ReConnect Res:" + handler.connect())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        setContentView(R.layout.activity_main)
        btn_imprimir.setOnClickListener {
            printtest()
        }
        btn_prender.setOnClickListener {
            try {
                settings.mPosPowerOn()
                if (handler.isConnected) {
                    sendMessage("Connect Res:" + handler.connect())
                } else {
                    handler.close()
                    sendMessage("ReConnect Res:" + handler.connect())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }


    private fun printtest() {

        val settings = app._setting
        val bb = app._setting.mPosEnterPrint()
        sendMessage("mPosEnterPrint:" + bb)
        if (bb) {
            //					settings.mPosPrintAlign(Settings.MPOS_PRINT_ALIGN_CENTER);
            //					settings.mPosPrnStr("Keative UPI Solutions");
            //					settings.mPosPrintAlign(Settings.MPOS_PRINT_ALIGN_LEFT);
            //					settings.mPosPrnStr("Remote Message from India-02");
            //					settings.mPosPrnStr("Date:30, 10月，2017  17:34:54");

            //					settings.mPosPrintAlign(Settings.MPOS_PRINT_ALIGN_CENTER);
            //					settings.mPosPrnStr("打印机测试");
            //					settings.mPosPrnStr("Printer test");
            //					settings.mPosPrnStr("打印机测试");
            //					settings.mPosPrnStr("123456");
            //					settings.mPosPrnStr("abcdef");
            //					settings.mPosPrnStr("******");
            //					settings.mPosPrnStr("\n\n");
            //					settings.mPosPrintFontSwitch(Settings.MPOS_PRINT_FONT_NEW);
            settings.mPosPrintAlign(Settings.MPOS_PRINT_ALIGN_CENTER)
            settings.mPosPrintTextSize(Settings.MPOS_PRINT_TEXT_DOUBLE_HEIGHT)
            settings.mPosPrnStr("POS Signed Order")
            settings.mPosPrintLn()
            settings.mPosPrintTextSize(Settings.MPOS_PRINT_TEXT_NORMAL)
            settings.mPosPrintAlign(Settings.MPOS_PRINT_ALIGN_LEFT)
            settings.mPosPrnStr("The cardholder stub   \nPlease properly keep")
            settings.mPosPrnStr("--------------------------")
            settings.mPosPrnStr("Merchant Name:ABC")
            settings.mPosPrnStr("Merchant No.:846584000103052")
            settings.mPosPrnStr("Terminal No.:12345678")
            settings.mPosPrnStr("categories: visa card")
            settings.mPosPrnStr("Period of Validity:2016/07")
            settings.mPosPrnStr("Batch no.:000101")
            settings.mPosPrnStr("Card Number:")
            settings.mPosPrnStr("622202400******0269")
            settings.mPosPrnStr("Trade Type:consumption")
            settings.mPosPrnStr("Serial No.:000024  \nAuthenticode:096706")
            settings.mPosPrnStr("Date/Time:2016/09/01 11:27:12")
            settings.mPosPrnStr("Ref.No.:123456789012345")
            settings.mPosPrnStr("Amount:$ 100.00")
            settings.mPosPrnStr("--------------------------")
            //					BitmapFactory.Options opt = new BitmapFactory.Options();
            //					opt.inPreferredConfig = Bitmap.Config.RGB_565;
            //					opt.inPurgeable = true;
            //					opt.inInputShareable = true;
            //					InputStream is = getResources().openRawResource(R.drawable.ooooo);
            //					Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
            //					settings.mPosPrintAlign(Settings.MPOS_PRINT_ALIGN_CENTER);
            //					settings.mPosPrnImg(bitmap);
            settings.mPosPrnStr("\n\n")
            //					if (!bitmap.isRecycled()) {
            //						bitmap.recycle();
            //					}
            //					bitmap = null;
            //				handleros.sendEmptyMessage(dismissDailog);
            txt_consola.append("SI FUNCIONA LA IMPRESORA\n")
        } else {
            txt_consola.append("NO FUNCIONA LA IMPRESORA\n")
        }
    }

    protected fun sendMessage(string: String) {
        Log.i("xtztt", "==>:" + string)
    }
}

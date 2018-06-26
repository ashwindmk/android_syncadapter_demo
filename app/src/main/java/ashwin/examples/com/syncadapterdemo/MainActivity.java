package ashwin.examples.com.syncadapterdemo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String AUTHORITY = "ashwin.examples.com.syncadapterdemo.provider";
    public static final String ACCOUNT_TYPE = "example.com";
    public static final String ACCOUNT = "dummyaccount";

    Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAccount = createSyncAccount(this);
    }

    public static Account createSyncAccount(Context context) {
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            Log.d("debug-logging", "account added successfully");
        } else {
            Log.d("debug-logging", "error adding account");
        }
        return newAccount;
    }

    public void startSingleSync(View view) {
        Bundle bundle = new Bundle();

        // required data
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        // custom data
        bundle.putString("key-1", "value-1");
        bundle.putString("key-2", "value-2");

        ContentResolver.requestSync(mAccount, AUTHORITY, bundle);
    }

    public void startPeriodicSync(View view) {
        long pollFrequency = 60 * 60;  // minimum poll frequency (in seconds) must be of 1 hour

        Bundle bundle = new Bundle();

        // custom data
        bundle.putString("key-1", "value-1");
        bundle.putString("key-2", "value-2");

        ContentResolver.addPeriodicSync(mAccount, AUTHORITY, bundle, pollFrequency);
    }

}

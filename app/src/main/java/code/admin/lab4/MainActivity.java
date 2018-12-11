package code.admin.lab4;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.widget.TabHost;
import android.app.TabActivity;
import android.widget.AdapterView;

public class MainActivity extends TabActivity implements View.OnClickListener {

    class RestaurantAdapter extends  ArrayAdapter<Restaurant>
    {
        public RestaurantAdapter(Context context, int textViewResourceId)
        {
            super(context, textViewResourceId);

        }

        public RestaurantAdapter()
        {
            super(MainActivity.this, android.R.layout.simple_list_item_1, restaurants);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if (row == null)
            {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.row, null);
            }

            Restaurant r = restaurants.get(position);

            ((TextView)row.findViewById(R.id.title)).setText(r.getName());
            ((TextView)row.findViewById(R.id.address)).setText(r.getAddress());
            ImageView icon = (ImageView)row.findViewById(R.id.icon);

            String type = r.getType();
            if (type.equals("Take out"))
            {
                icon.setImageResource(R.drawable.t);
            }
            else if (type.equals("Sit down"))
            {
                icon.setImageResource(R.drawable.s);
            }
            else
            {
                icon.setImageResource(R.drawable.d);
            }

            return row;
        }
    }


    private List<Restaurant> restaurants = new ArrayList<Restaurant>();
    private RestaurantAdapter adapter = null;

    Button btnSave;
    EditText edtName;
    EditText edtAddress;
    RadioGroup rgpTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddr);
        rgpTypes = findViewById(R.id.rgpTypes);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        ListView lsvRestaurants = findViewById(R.id.restaurants);

        adapter = new RestaurantAdapter();

        lsvRestaurants.setAdapter(adapter);
        lsvRestaurants.setOnItemClickListener(onItemClickListener);

        TabHost.TabSpec spec = getTabHost().newTabSpec("tag1");
        spec.setContent(R.id.restaurants);
        spec.setIndicator("List", getResources().getDrawable(R.drawable.list));
        getTabHost().addTab(spec);

        spec = getTabHost().newTabSpec("tag2");
        spec.setContent(R.id.details);
        spec.setIndicator("Details", getResources().getDrawable(R.drawable.restaurant));
        getTabHost().addTab(spec);

        getTabHost().setCurrentTab(0);
    }


    @Override
    public void onClick(View v) {


        Restaurant r = new Restaurant();

        r.setName(edtName.getText().toString());
        r.setAddress(edtAddress.getText().toString());

        RadioGroup rgpTypes = findViewById(R.id.rgpTypes);
        switch (rgpTypes.getCheckedRadioButtonId())
        {
            case R.id.rbtTakeOut:
                r.setType("Take out");
                break;
            case R.id.rbtSitDown:
                r.setType("Sit down");
                break;
            case R.id.rbtDelivery:
                r.setType("Delivery");
                break;
        }

        restaurants.add(r);
        adapter.notifyDataSetChanged();
//        String msg = r.getName() + " " + r.getAddress() + " " + r.getType();
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Restaurant r = restaurants.get(position);

            edtName.setText(r.getName());
            edtAddress.setText(r.getAddress());
            if (r.getType().equals("Take out"))
            {
                rgpTypes.check(R.id.rbtTakeOut);
            }
            else if (r.getType().equals("Sit down"))
            {
                rgpTypes.check(R.id.rbtSitDown);
            }
            else
            {
                rgpTypes.check(R.id.rbtDelivery);
            }

            getTabHost().setCurrentTab(1);
        }
    };



    //end
}

package im.amomo.volley.sample.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import im.amomo.volley.OkRequest;
import im.amomo.volley.sample.R;
import im.amomo.volley.toolbox.OkVolley;
import org.json.JSONObject;

/**
 * Created by GoogolMo on 12/31/13.
 */
public class RequestFragment extends ListFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.title, new String[]{
                "Request GoogolMo's Profile"}));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (position == 0) {
            load();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListShown(true);
    }

    private void load() {

        OkRequest request = new BaseRequest(Request.Method.GET, "https://api.douban.com/v2/user/googolmo",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Toast.makeText(getActivity(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), jsonObject.optString("name"), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        request.setTag("request");
        OkVolley.getInstance().getRequestQueue().add(request);

    }


    @Override
    public void onDestroy() {
        OkVolley.getInstance().getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return request.getTag() != null && request.getTag().equals("request");
            }
        });
        super.onDestroy();
    }


}

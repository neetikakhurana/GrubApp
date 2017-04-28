package com.lecture.nitika.grub;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lecture.nitika.grub.Helper.RecipeItem;
import com.mashape.p.spoonacularrecipefoodnutritionv1.http.client.APICallBack;
import com.mashape.p.spoonacularrecipefoodnutritionv1.http.client.HttpContext;
import com.mashape.p.spoonacularrecipefoodnutritionv1.models.DynamicResponse;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeInfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeInfo extends Fragment implements View.OnClickListener {

    private static final String TAG = "RecipeInfo";
    private OnFragmentInteractionListener mListener;
    private RecipeItem recipeItem = null;
    private ImageView imageView = null;
    private Button btn_Ingrediants = null;
    private Button btn_Directions = null;
    private TextView txtView_title = null;
    private TextView txtView_description = null;

    private String ingredientsTotal = "";
    private String proceedure = "";
    private boolean isIngriedientClicked = false;


    public RecipeInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param rItem Recipe Item clicked on the list view
     * @return A new instance of fragment RecipeInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeInfo newInstance(RecipeItem rItem) {
        RecipeInfo fragment = new RecipeInfo();
        Bundle args = new Bundle();
        args.putSerializable(Constants.clicked_ListItem_Key,rItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeItem = (RecipeItem)getArguments().getSerializable(Constants.clicked_ListItem_Key);
            Log.d(TAG,recipeItem.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_recipe_info, container, false);

        imageView = (ImageView)rootView.findViewById(R.id.image_recipeInfo);
        txtView_title = (TextView)rootView.findViewById(R.id.txtView_recipeInfo_title);
        txtView_description = (TextView)rootView.findViewById(R.id.txtView_recipeInfo_Description);
        txtView_description.setMovementMethod(new ScrollingMovementMethod());
        btn_Directions = (Button)rootView.findViewById(R.id.btn_Directions);
        btn_Ingrediants = (Button)rootView.findViewById(R.id.btn_Ingrediants);
        btn_Directions.setOnClickListener(this);
        btn_Ingrediants.setOnClickListener(this);
        btn_Ingrediants.performClick();

        new ImageDownloadTask(imageView).execute(recipeItem.getContent().getImage());
        txtView_title.setText(recipeItem.getContent().getTitle());


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        getRecipeInformation();
    }

    private void getRecipeInformation() {
        RecipeClient rClient = RecipeClient.getInstance(getContext());
        rClient.controller.getRecipeInformationAsync(recipeItem.getContent().getId(), new APICallBack<DynamicResponse>() {
            @Override
            public void onSuccess(HttpContext context, DynamicResponse response) {
                try {
                    Log.d(TAG,response.parseAsDictionary().toString());
                    Map<String, Object> responseMap = response.parseAsDictionary();

                    if(responseMap.containsKey(Constants.ingredients_key)){
                        List<Map<String,Object>> ingredientslist = (List<Map<String,Object>>)responseMap.get(Constants.ingredients_key);
                        Log.d(TAG,"Number of Ingredients Required::"+ingredientslist.size());

                        StringBuilder sb = new StringBuilder();

                        for (Map<String, Object> entry : ingredientslist){

                            sb.append("<font color=#5BD43C>");
                            if(entry.containsKey(Constants.ingredients_amount_key)){
                                sb.append(entry.get(Constants.ingredients_amount_key)+" - ");
                            }

                            if(entry.containsKey(Constants.ingredients_unit_key)){
                                sb.append(entry.get(Constants.ingredients_unit_key)+" ");
                            }
                            sb.append("</font>");

                            sb.append("<font color=#000000>");
                            if(entry.containsKey(Constants.ingredients_name_key)){
                                sb.append(entry.get(Constants.ingredients_name_key));
                            }
                            sb.append("</font><br><br>");
                        }
                        ingredientsTotal = sb.toString();
                        Log.d(TAG,ingredientsTotal);
                    }

                    if(responseMap.containsKey(Constants.direction_key)){
                        List<Map<String,Object>> directionList = (List<Map<String,Object>>)responseMap.get(Constants.direction_key);


                        StringBuilder sb = new StringBuilder();

                        for (Map<String, Object> entry : directionList){

                            if(entry.containsKey(Constants.direction_step_key)){
                                List<Map<String,Object>> stepList = (List<Map<String,Object>>)entry.get(Constants.direction_step_key);
                                Log.d(TAG,"Number of Directions ::"+stepList.size());

                                for(Map<String,Object> stepentry : stepList){

                                    sb.append("<font color=#5BD43C> Step ");
                                    if(stepentry.containsKey(Constants.direction_number_key)){
                                        sb.append(stepentry.get(Constants.direction_number_key)+" : ");
                                    }
                                    sb.append("</font>");
                                    sb.append("<font color=#000000>");
                                    if(stepentry.containsKey(Constants.direction_innerStep_key)){
                                        sb.append(stepentry.get(Constants.direction_innerStep_key)+" ");
                                    }
                                    sb.append("</font><br><br>");
                                }
                            }
                        }
                        proceedure = sb.toString();
                        Log.d(TAG,proceedure);
                    }

                    Handler mHandler = new Handler();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            UpdateDirections();
                        }
                    });


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpContext context, Throwable error) {
                Log.d(TAG,error.getLocalizedMessage());
            }
        });


    }

    private void UpdateDirections() {
        if(isIngriedientClicked) {
            txtView_description.setText(Html.fromHtml(ingredientsTotal));
        }else{
            txtView_description.setText(Html.fromHtml(proceedure));
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_Ingrediants:
                isIngriedientClicked = true;
                btn_Directions.setBackground(getResources().getDrawable(R.drawable.recipeinfobuttonright));
                btn_Directions.setTextColor(getResources().getColor(R.color.white));
                btn_Ingrediants.setTextColor(getResources().getColor(R.color.colorLogin));
                btn_Ingrediants.setBackground(getResources().getDrawable(R.drawable.plainbtn));
                UpdateDirections();
                break;
            case R.id.btn_Directions:
                isIngriedientClicked = false;
                btn_Ingrediants.setBackground(getResources().getDrawable(R.drawable.recipeinfobuttonleft));
                btn_Ingrediants.setTextColor(getResources().getColor(R.color.white));
                btn_Directions.setTextColor(getResources().getColor(R.color.colorLogin));
                btn_Directions.setBackground(getResources().getDrawable(R.drawable.plainbtn));
                UpdateDirections();
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class ImageDownloadTask extends AsyncTask<String,Void,Bitmap>{

        ImageView imgView = null;
        public ImageDownloadTask(ImageView mImageView){
            this.imgView = mImageView;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            URL imageURL = null;
            Bitmap bitmapfrmServer = null;
            try {
                imageURL = new URL(params[0]);
                bitmapfrmServer = BitmapFactory.decodeStream(imageURL.openStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return bitmapfrmServer;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
           if(bitmap != null){
               imgView.setImageBitmap(bitmap);
           }
        }
    }
}

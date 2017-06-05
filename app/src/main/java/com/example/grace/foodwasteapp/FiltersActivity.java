package com.example.grace.foodwasteapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class FiltersActivity extends AppCompatActivity {

    HashMap<String, List<String>> filterTypes;
    List<String> filterTypesList;
    ExpandableListView expandableListView;
    FilterTypesAdapter adapter;
    HashSet<String> allergiesSelected;
    HashSet<String> dietsSelected;
    HashSet<String> cuisinesSelected;
    HashMap<String,String> callValues;//this is a hardcoded. to retrieve argument for
    //certain dietary and allergic restrictions, I would have to parse a JSONP file, instead of
    //a JSON file. I did some research that said that it was possible to use the retrofit library
    //to parse the file, but I could not parse both JSONP and JSON files in the same program. And
    //because of the limited time, I felt that I could not learn to parse JSONP and use the data
    // in a timely mannner.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        allergiesSelected = new HashSet<String>();
        dietsSelected = new HashSet<String>();
        cuisinesSelected = new HashSet<String>();

        filterTypes = new HashMap<String, List<String>>();//TODO if time permits, add other things like holidays etc

        List<String> diets = new ArrayList<String>();
        diets.add("Vegetarian");
        diets.add("Vegan");
        diets.add("Pescetarian");
        diets.add("Ovo vegetarian");
        diets.add("Lacto vegetarian");

        List<String> allergies = new ArrayList<String>();
        allergies.add("Dairy");
        allergies.add("Egg");
        allergies.add("Gluten");
        allergies.add("Peanut");
        allergies.add("Seafood");
        allergies.add("Sesame");
        allergies.add("Soy");
        allergies.add("Sulfite");
        allergies.add("Tree Nut");
        allergies.add("Wheat");

        List<String> cuisines = new ArrayList<String>();
        cuisines.add("American");
        cuisines.add("Italian");
        cuisines.add("Asian");
        cuisines.add("Mexican");
        cuisines.add("Indian");
        cuisines.add("Chinese");
        cuisines.add("French");
        cuisines.add("Spanish");

        filterTypes.put("Diets", diets);
        filterTypes.put("Allergies", allergies);
        filterTypes.put("Cuisines", cuisines);

        filterTypesList = new ArrayList<String>(filterTypes.keySet());

        expandableListView = (ExpandableListView) findViewById(R.id.elvFilterTypes);

        //build adapter
        adapter = new FilterTypesAdapter();
//
//        //config list view
        expandableListView.setAdapter(adapter);

//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Toast.makeText(FiltersActivity.this, filterTypes.get(filterTypesList.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();                return true;
//            }
//        });

        callValues = new HashMap<String, String>();
        //http://api.yummly.com/v1/api/metadata/diet?_app_id=26edbdd7&_app_key=e821c0abc1c766b3cd3f2a2c23023113
        callValues.put("Vegetarian","387%5ELacto-ovo vegetarian");
        callValues.put("Vegan","386%5EVegan");
        callValues.put("Pescetarian","390%5EPescetarian");
        callValues.put("Ovo vegetarian","389%5EOvo vegetarian");
        callValues.put("Lacto vegetarian","388%5ELacto vegetarian");

        //http://api.yummly.com/v1/api/metadata/allergy?_app_id=26edbdd7&_app_key=e821c0abc1c766b3cd3f2a2c23023113
        callValues.put("Gluten","393%5EGluten-Free");
        callValues.put("Dairy","396%5EDairy-Free");
        callValues.put("Egg","397%5EEgg-Free");
        callValues.put("Peanut","394%5EPeanut-Free");
        callValues.put("Seafood","398%5ESeafood-Free");
        callValues.put("Sesame","399%5ESesame-Free");
        callValues.put("Soy","400%5ESoy-Free");
        callValues.put("Sulfite","401%5ESulfite-Free");
        callValues.put("Tree Nut","395%5ETree Nut-Free");
        callValues.put("Wheat","392%5EWheat-Free");

        //http://api.yummly.com/v1/api/metadata/cuisine?_app_id=26edbdd7&_app_key=e821c0abc1c766b3cd3f2a2c23023113
        callValues.put("American","cuisine%5Ecuisine-american");
        callValues.put("Italian","cuisine%5Ecuisine-italian");
        callValues.put("Asian","cuisine%5Ecuisine-asian");
        callValues.put("Mexican","cuisine%5Ecuisine-mexican");
        callValues.put("Indian","cuisine%5Ecuisine-indian");
        callValues.put("Chinese","cuisine%5Ecuisine-chinese");
        callValues.put("French","cuisine%5Ecuisine-french");
        callValues.put("Spanish","cuisine%5Ecuisine-spanish");




    }

    private class FilterTypesAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return filterTypes.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return filterTypes.get(filterTypesList.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return filterTypesList.get(groupPosition);
        }

        //returns current child as an object
        @Override
        public Object getChild(int parent, int child) {
            return filterTypes.get(filterTypesList.get(parent)).get(child);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int parent, int child) {
            return child;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentView) {
            String filterTypeTitle = (String) getGroup(parent);
            if (convertView == null) {
                LayoutInflater inflator = (LayoutInflater) FiltersActivity.this.getLayoutInflater();
                convertView = inflator.inflate(R.layout.list_simple, parentView, false);
            }
            TextView filterTypeTextView = (TextView) convertView.findViewById(R.id.tvSimpleItem);
            filterTypeTextView.setTypeface(null, Typeface.BOLD);
            filterTypeTextView.setText("        " + filterTypeTitle);
            return convertView;
        }

        @Override
        public View getChildView(int parent, int child, boolean isLastChild, View convertView, ViewGroup parentView) {
            String filterTitle = (String) getChild(parent, child);//returns current child

            //if convertView is null it must be created
            if (convertView == null) {
                LayoutInflater inflator = (LayoutInflater) FiltersActivity.this.getLayoutInflater();
                convertView = inflator.inflate(R.layout.list_checkboxes, parentView, false);
            }
            final int finalParent = parent;
            final int finalChild = child;
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // notify for main view by using another listener
                    Toast.makeText(FiltersActivity.this, filterTypes.get(filterTypesList.get(finalParent)).get(finalChild), Toast.LENGTH_SHORT).show();
                }
            });

            CheckBox filterTextView = (CheckBox) convertView.findViewById(R.id.cbFilter);
            filterTextView.setText(filterTitle);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    //this method is called whenever a checkbox of a filter is clicked
    //if it is clicked, then that checkbox item is added to the corresponding selected filter list
    protected void onClickCheckBox(View v){
        CheckBox checkBox = (CheckBox)v.findViewById(R.id.cbFilter);
        String checkBoxText = checkBox.getText().toString();
        if(checkBox.isChecked()){
            if(filterTypes.get("Allergies").contains(checkBoxText)) {//checked item is an allergy
                allergiesSelected.add(callValues.get(checkBoxText));
//                allergiesSelected.add(checkBoxText);
            }
            else if(filterTypes.get("Diets").contains(checkBoxText)){//checked item is a diet
                dietsSelected.add(callValues.get(checkBoxText));
//                dietsSelected.add(checkBoxText);
            }
            else{//checked item is a cuisine
                cuisinesSelected.add(callValues.get(checkBoxText));
            }
        }
        else{
            if(filterTypes.get("Allergies").contains(checkBoxText)) {//unchecked item is an allergy
                allergiesSelected.remove(callValues.get(checkBoxText));
//                allergiesSelected.remove(checkBoxText);
            }
            else if(filterTypes.get("Diets").contains(checkBoxText)){//unchecked item is a diet
                dietsSelected.remove(callValues.get(checkBoxText));
//                dietsSelected.remove(checkBoxText);
            }
            else{//unchecked item is a cuisine
                cuisinesSelected.remove(callValues.get(checkBoxText));
            }
        }
    }

    protected void onClickFilter(View v){
        Intent toFindRecipe = new Intent(this, FindRecipeActivity.class);

        if(allergiesSelected.size() !=0)
            toFindRecipe.putStringArrayListExtra("allergies selected", new ArrayList<String>(allergiesSelected));
        if(dietsSelected.size() != 0)
            toFindRecipe.putStringArrayListExtra("diets selected", new ArrayList<String>(dietsSelected));
        if(cuisinesSelected.size() !=0){
            toFindRecipe.putStringArrayListExtra("cuisines selected", new ArrayList<String>(cuisinesSelected));
        }
        toFindRecipe.putExtra("query", getIntent().getStringExtra("query"));//pass query back
        startActivity(toFindRecipe);
    }
}
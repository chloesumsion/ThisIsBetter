package s3.thisisbetter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;

import s3.thisisbetter.AppConstants;
import s3.thisisbetter.R;
import s3.thisisbetter.activities.CreateEventActivity;
import s3.thisisbetter.activities.ViewResponseActivity;
import s3.thisisbetter.adapters.EventOwnedArrayAdapter;
import s3.thisisbetter.model.DB;
import s3.thisisbetter.model.Event;
import s3.thisisbetter.model.EventListItem;

/**
 * The fragment for the Events I Own tab
 */
public class EventsIOwnFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public final static String PARENT_TYPE = "events_i_own";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private EventOwnedArrayAdapter adapter;

    public EventsIOwnFragment() { }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EventsIOwnFragment newInstance(int sectionNumber) {
        EventsIOwnFragment fragment = new EventsIOwnFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events_i_own, container, false);
        setUpListView(rootView);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateEventActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void setUpListView(View rootView) {
        // Create the firebase query that grabs all of the events I own.
        String uid = DB.getMyUID();
        Query queryRef = DB.getEventsRef().orderByChild(Event.OWNER_KEY).equalTo(uid);
        queryRef.addChildEventListener(eventListener);
        DB.monitorChildListener(queryRef, eventListener);

        // Set up the adapter
        adapter = new EventOwnedArrayAdapter(rootView.getContext(), new ArrayList<EventListItem>());
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        setUpListClickListener(listView);
    }

    private void setUpListClickListener(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventListItem item = adapter.getItem(position);
                if(item.isSection()) { return; }

                String eventID = adapter.getEventID(item);

                Intent intent = new Intent(getActivity(), ViewResponseActivity.class);
                intent.putExtra(AppConstants.EXTRA_EVENT_ID, eventID);
                intent.putExtra(AppConstants.EXTRA_PARENT_TYPE, PARENT_TYPE);
                startActivity(intent);
            }
        });
    }

    private ChildEventListener eventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Event e = dataSnapshot.getValue(Event.class);
            adapter.addEvent(e, dataSnapshot.getKey());
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Event e = dataSnapshot.getValue(Event.class);
            adapter.editEvent(e, dataSnapshot.getKey());
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Event e = dataSnapshot.getValue(Event.class);
            adapter.deleteEvent(e);
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

        @Override
        public void onCancelled(FirebaseError firebaseError) {}
    };

}

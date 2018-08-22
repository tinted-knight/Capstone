package com.t_knight.and.capstone.job_dispatcher;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.t_knight.and.capstone.local_db.TopicListRepo;
import com.t_knight.and.capstone.model.FireContent;
import com.t_knight.and.capstone.model.TopicDescription;

import java.util.List;

import timber.log.Timber;

public class TopicListJobService extends JobService {

    private AsyncTask task;

    @SuppressLint("StaticFieldLeak") @Override public boolean onStartJob(final JobParameters job) {
        task = new AsyncTask() {

            @Override protected Object doInBackground(Object[] objects) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dbRef = database.getReference();

                Query query = dbRef.child("content");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        FireContent data = dataSnapshot.getValue(FireContent.class);
                        fillDatabase(data.getTopicDescriptions());
                    }

                    @Override public void onCancelled(@NonNull DatabaseError databaseError) {
                        Timber.d("onCancelled: %s", databaseError.getMessage());
                    }
                });
                return null;
            }

            private void fillDatabase(List<TopicDescription> data) {
                TopicListRepo local = new TopicListRepo(getApplicationContext());
                local.fillTopicList(data);
            }

            @Override protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                jobFinished(job, false);
            }
        };
        task.execute();
        return true;
    }

    @Override public boolean onStopJob(JobParameters job) {
        Timber.i("============== onStopJob");
        if (task != null)
            task.cancel(true);
        return true;
    }

}

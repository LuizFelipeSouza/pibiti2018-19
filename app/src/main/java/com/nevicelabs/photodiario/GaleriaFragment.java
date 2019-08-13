package com.nevicelabs.photodiario;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GaleriaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GaleriaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GaleriaFragment extends Fragment {

    private OnPostagemSelecionadaListener mListener;

    public GaleriaFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            Postagem post = EditorFragmentArgs.fromBundle(getArguments()).getPostagem();
            configurarRecycler(post);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_galeria, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (OnPostagemSelecionadaListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     * See the Android Training lesson
     * http://developer.android.com/training/basics/fragments/communicating.html
     * Communicating with Other Fragments for more information.
     *
     * O guia de desenvolvedores explica a necesidade desta interface
     * https://developer.android.com/guide/components/fragments#CommunicatingWithActivity
     */
    public interface OnPostagemSelecionadaListener {
        void onPostagemSeleionada(Uri uri);
    }

    public void configurarRecycler(Postagem post) {
        final RecyclerView mRecyclerView = getActivity().findViewById(R.id.galeria_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        TextView mensagemPadrao = getActivity().findViewById(R.id.empty_textview);
        mensagemPadrao.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

        ArrayList postagens = new ArrayList<Postagem>();
        postagens.add(post);

        LinearAdapter mAdapter = new LinearAdapter(postagens);
        mRecyclerView.setAdapter(mAdapter);

        // Aqui dizemos que cada item da RecyclerView é clicável
        mRecyclerView.addOnItemTouchListener(
                new RecyclerView.OnItemTouchListener() {
                    @Override
                    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                        Toast.makeText(getActivity(), "Postagem selecionada", Toast.LENGTH_SHORT).show();
                        // Navigation.findNavController(mRecyclerView).navigate(R.id.galeria_to_postagem);
                        return false;
                    }

                    @Override
                    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {}

                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
                }
        );

        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
}

package com.nevicelabs.photodiario;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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


public class GaleriaFragment extends Fragment {
    /* TODO: Talvez seja mais interessante fazer com que este fragment extenda ListFragment
     * A justificativa é que isso serviria para lidar com os eventos de seleção de itens da
     * RecyclerView. Dessa forma, sempre que o usuário tocar um item da lista, o método
      * onListItemClick() será chamado pelo sistema, que por sua vez chamará o método
      * onPostagemSelecionada(), vide: https://developer.android.com/guide/components/fragments#EventCallbacks
     */

    private OnPostagemSelecionadaListener mListener;

    public GaleriaFragment() {}

    /**
     * Método chamado durane a criação do fragment. Aqui verificamos se
     * há alguma postagem sendo passada ao fragmento para que seja exibida
     * a lista de postagens ou a tela de empty state.
     * @param savedInstanceState
     */
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

    /**
     * Este método faz parte do ciclo de vida do fragment
     * e é chamado ao vincular este fragment à Acivity.
     * Verifica se o fragment implementa a interface que
     * escuta os eventos de click nos itens da RecyclerView.
     * Lança uma exception se a interface não for implementada.
     *
     * "Caso a atividade não tenha implementado a interface, o fragmento
     * lançará ClassCastException. Se for bem-sucedida, o membro
     * mListener reterá uma referência da implementação da atividade de
     * OnArticleSelectedListener, para que o fragmento A possa
     * compartilhar os eventos com a atividade chamando métodos
     * definidos pela interface OnArticleSelectedListener. Por exemplo: se
     * o fragmento A for uma extensão de ListFragment, sempre que o
     * usuário clicar em um item de lista, o sistema chamará
     * onListItemClick() no fragmento, que, em seguida, chamará
     * onArticleSelected() para compartilhar o evento com a atividade".
     *
     * @param context A activity que hospeda este Fragment.
     */
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
     * O guia de desenvolvedores explica a necessidade desta interface
     * https://developer.android.com/guide/components/fragments#CommunicatingWithActivity
     */
    public interface OnPostagemSelecionadaListener {
        void onPostagemSeleionada(Uri uriPostagem);
    }

    /**
     * Método chamado durante a criação do fragment quando há pelo menos uma postagem a
     * ser exibida. Quando o número de postagens for zero, é exibida uma tela Empty State.
     * @param post Postagem a ser adicionada à galeria. Ou seja, à RecyclerView
     */
    private void configurarRecycler(Postagem post) {
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

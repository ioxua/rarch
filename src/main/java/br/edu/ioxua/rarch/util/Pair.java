package br.edu.ioxua.rarch.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Pair<K, L> {
    private final K first;
    private final L second;

    public String toString() {
        return "{" + first.toString() + ":" + second.toString() + "}";
    }
}
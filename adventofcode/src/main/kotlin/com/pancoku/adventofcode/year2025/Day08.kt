package com.pancoku.adventofcode.year2025

import com.google.auto.service.AutoService
import com.pancoku.adventofcode.solver.DaySolver

/**
 * Copyright ©2026 [Pancoku]. All rights reserved.
 * Day08
 * <p>
 * @author lijun
 * @since 2026-11-10 14:57
 * @version 1.0
 * 
 */
@AutoService(DaySolver::class)
class Day08 : DaySolver {

    override val day = 8
    override val year = 2025

    data class Point(val x: Long, val y: Long, val z: Long)

    data class Edge(val u: Int, val v: Int, val distance: Long)

    class UnionFind(val size: Int) {
        private val parent = IntArray(size) { i -> i }
        private val rank = IntArray(size) { 0 }
        val componentSize = IntArray(size) { 1 }
        var components = size
            private set

        fun find(x: Int): Int {
            if (x == parent[x]) return x
            parent[x] = find(parent[x])
            return parent[x]
        }

        fun union(x: Int, y: Int): Boolean {
            val rootX = find(x)
            val rootY = find(y)
            if (rootX == rootY) return false

            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY
                componentSize[rootY] += componentSize[rootX]
            } else {
                parent[rootY] = rootX
                componentSize[rootX] += componentSize[rootY]
                if (rank[rootX] == rank[rootY]) {
                    rank[rootX]++
                }
            }
            components--
            return true
        }

    }

    override fun solvePart1(input: String): String {
        val points = input.lines()
            .filter { it.isNotBlank() }
            .map {
                val (x, y, z) = it.split(',').map { n -> n.trim().toLong() }
                Point(x, y, z)
            }
        val answer = solve1(points)
        return "\nProcessed ${points.size} lines. The answer is $answer."
    }

    private fun solve1(points: List<Point>) : Long{
        val edges = mutableListOf<Edge>()
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val dx = (points[i].x - points[j].x)
                val dy = (points[i].y - points[j].y)
                val dz = (points[i].z - points[j].z)
                edges.add(Edge(i, j, dx * dx + dy * dy + dz * dz))
            }
        }
        edges.sortBy { it.distance }

        val uf = UnionFind(points.size)
        val edgesToProcess = edges.take(1000)
        for ((u, v) in edgesToProcess) {
            uf.union(u, v)
        }

        return points.indices
            .asSequence()
            .filter { uf.find(it) == it }
            .map { uf.componentSize[it] }
            .sortedDescending()
            .take(3)
            .fold(1L) { acc, size -> acc * size }
    }

}
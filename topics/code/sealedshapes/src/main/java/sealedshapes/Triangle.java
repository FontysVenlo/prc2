/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sealedshapes;

/**
 * Simple triangle.
 * 
 * @author Pieter van den Hombergh {@code <pieter.van.den.hombergh@gmail.com>}
 * 
 * @param a corner of triangle
 * @param b corner of triangle
 * @param c corner of triangle
 */
public record Triangle(BasicPoint a, BasicPoint b, BasicPoint c) implements BasicShape {

}
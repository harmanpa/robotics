/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.visitor;

/**
 *
 * @author peter
 */
public interface SdfElementVisitor {

    public <T> boolean visit(Class<T> type, T value);
}

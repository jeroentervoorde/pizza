package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.domain.Category;
import org.agoncal.application.petstore.domain.Item;
import org.agoncal.application.petstore.domain.Product;
import org.agoncal.application.petstore.exception.ValidationException;
import org.agoncal.application.petstore.util.Loggable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import org.agoncal.application.petstore.util.Cacheable;
import org.agoncal.application.petstore.util.ObjectCache;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@Stateless
@Loggable
public class CatalogService implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private EntityManager em;

    // ======================================
    // =              Public Methods        =
    // ======================================

    @Cacheable
    public Category findCategory(Long categoryId) {
        if (categoryId == null)
            throw new ValidationException("Invalid category id");

        return em.find(Category.class, categoryId);
    }

    @Cacheable
    public Category findCategory(String categoryName) {
        if (categoryName == null)
            throw new ValidationException("Invalid category name");

        TypedQuery<Category> typedQuery = em.createNamedQuery(Category.FIND_BY_NAME, Category.class);
        typedQuery.setParameter("pname", categoryName);
        return typedQuery.getSingleResult();
    }

    public List<Category> findAllCategories() {
        TypedQuery<Category> typedQuery = em.createNamedQuery(Category.FIND_ALL, Category.class);
        return typedQuery.getResultList();
    }

    public Category createCategory(Category category) {
        if (category == null)
            throw new ValidationException("Category object is null");

        em.persist(category);
        return category;
    }

    @Cacheable
    public Category updateCategory(Category category) {
        if (category == null)
            throw new ValidationException("Category object is null");

        return em.merge(category);
    }

    @Cacheable
    public void removeCategory(Category category) {
        if (category == null)
            throw new ValidationException("Category object is null");

        em.remove(em.merge(category));
    }

    @Cacheable
    public void removeCategory(Long categoryId) {
        if (categoryId == null)
            throw new ValidationException("Invalid category id");

        removeCategory(findCategory(categoryId));
    }

    public List<Product> findProducts(String categoryName) {
        if (categoryName == null)
            throw new ValidationException("Invalid category name");

        TypedQuery<Product> typedQuery = em.createNamedQuery(Product.FIND_BY_CATEGORY_NAME, Product.class);
        typedQuery.setParameter("pname", categoryName);
        return typedQuery.getResultList();
    }

    @Cacheable
    public Product findProduct(Long productId) {
        if (productId == null)
            throw new ValidationException("Invalid product id");

        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {}
        
        Product product = em.find(Product.class, productId);
        if (product != null) {
            product.getItems(); // TODO check lazy loading
        }
        return product;
    }

    public List<Product> findAllProducts() {
        TypedQuery<Product> typedQuery = em.createNamedQuery(Product.FIND_ALL, Product.class);
        return typedQuery.getResultList();
    }

    @Cacheable
    public Product createProduct(Product product) {
        if (product == null)
            throw new ValidationException("Product object is null");

        if (product.getCategory() != null && product.getCategory().getId() == null)
            em.persist(product.getCategory());

        em.persist(product);
        return product;
    }

    @Cacheable
    public Product updateProduct(Product product) {
        if (product == null)
            throw new ValidationException("Product object is null");

        return em.merge(product);
    }

    @Cacheable
    public void removeProduct(Product product) {
        if (product == null)
            throw new ValidationException("Product object is null");

        em.remove(em.merge(product));
    }

    @Cacheable
    public void removeProduct(Long productId) {
        if (productId == null)
            throw new ValidationException("Invalid product id");

        removeProduct(findProduct(productId));
    }

    public List<Item> findItems(Long productId) {
        if (productId == null)
            throw new ValidationException("Invalid product id");

        TypedQuery<Item> typedQuery = em.createNamedQuery(Item.FIND_BY_PRODUCT_ID, Item.class);
        typedQuery.setParameter("productId", productId);
        return typedQuery.getResultList();
    }

    @Cacheable
    public Item findItem(final Long itemId) {
        if (itemId == null)
            throw new ValidationException("Invalid item id");

        return em.find(Item.class, itemId);
    }

    public List<Item> searchItems(String keyword) {
        if (keyword == null)
            keyword = "";

        TypedQuery<Item> typedQuery = em.createNamedQuery(Item.SEARCH, Item.class);
        typedQuery.setParameter("keyword", "%" + keyword.toUpperCase() + "%");
        return typedQuery.getResultList();
    }

    public List<Item> findAllItems() {
        TypedQuery<Item> typedQuery = em.createNamedQuery(Item.FIND_ALL, Item.class);
        return typedQuery.getResultList();
    }

    @Cacheable
    public Item createItem(Item item) {
        if (item == null)
            throw new ValidationException("Item object is null");

        if (item.getProduct() != null && item.getProduct().getId() == null) {
            em.persist(item.getProduct());
            if (item.getProduct().getCategory() != null && item.getProduct().getCategory().getId() == null)
                em.persist(item.getProduct().getCategory());
        }

        em.persist(item);
        return item;
    }

    @Cacheable
    public Item updateItem(Item item) {
        if (item == null)
            throw new ValidationException("Item object is null");

        return em.merge(item);
    }
    
    @Cacheable
    public void removeItem(Item item) {
        if (item == null)
            throw new ValidationException("Item object is null");

        em.remove(em.merge(item));
    }

    @Cacheable
    public void removeItem(Long itemId) {
        if (itemId == null)
            throw new ValidationException("itemId is null");

        removeItem(findItem(itemId));
    }
}

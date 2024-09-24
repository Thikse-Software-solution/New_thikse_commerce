import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../../services/product.service';
import { Product } from '../../services/product.model';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-product-management',
  templateUrl: './product-management.component.html',
  styleUrls: ['./product-management.component.scss']
})
export class ProductManagementComponent implements OnInit {
  productForm: FormGroup;
  showCategory = false;
  showSubCategory = false; // Add this line
  categoryOptions: string[] = [];
  subCategoryOptions: string[] = [];
  products: Product[] = [];
   mainImage: File | null = null;
  thumbnail: File | null = null;
  threeDImages: File[] = [];
  images: File[] = [];
editingProductId: number | null = null;
  categorySubCategoryMap: { [key: string]: string[] } = {
    'Baby Care': [
  "Baby Shampoo",
  "Baby Lotion",
  "Baby Wipes",
  "Baby Diapers",
  "Baby Powder",
  "Baby Oil"
],
    'body care': ["Body Lotion", "Body Wash", "Body Scrub", "Body Butter", "Body Oil", "Body Mist"],
    'face care':["Face Wash", "Moisturizer", "Face Serum", "Toner", "Exfoliator", "Face Mask", "Sunscreen", "Eye Cream", "Facial Oil", "Cleanser", "Face Scrub", "Spot Treatment"],
    'skin care':["Cleanser", "Toner", "Moisturizer", "Serum", "Exfoliator", "Face Mask", "Sunscreen", "Eye Cream", "Night Cream", "Facial Oil", "Spot Treatment", "Peeling Gel"],
    'hair care':["Shampoo", "Conditioner", "Hair Mask", "Hair Oil", "Leave-In Conditioner", "Serum", "Hair Spray", "Dry Shampoo", "Hair Gel", "Mousse", "Heat Protectant", "Hair Treatment"],
    'new launches':[
  "Baby Shampoo", "Baby Lotion", "Baby Wipes", "Baby Diapers", "Baby Powder", "Baby Oil",
  "Body Lotion", "Body Wash", "Body Scrub", "Body Butter", "Body Oil", "Body Mist",
  "Cleanser", "Toner", "Moisturizer", "Serum", "Exfoliator", "Face Mask", "Sunscreen", "Eye Cream", "Night Cream", "Facial Oil", "Spot Treatment", "Peeling Gel",
  "Shampoo", "Conditioner", "Hair Mask", "Hair Oil", "Leave-In Conditioner", "Serum", "Hair Spray", "Dry Shampoo", "Hair Gel", "Mousse", "Heat Protectant", "Hair Treatment"
]


    // 'Necklace': ['dimand', 'gold'],
    // 'Ring': ['dimand ring', 'gold ring'],
  };

  constructor(private fb: FormBuilder,private http: HttpClient, private productService: ProductService) {
    this.productForm = this.fb.group({
      id: [null],
      shine: [false],
      sheShine: [false],
      subCategory: [''],
      category: ['', Validators.required],
      mainimage: [''],
      image: [''],
      text: [''],
      cardTitle: [''],
      images: [''],
      thumbnail: [''],
      title: ['', Validators.required],
      name: ['', Validators.required],
      benefit: [''],
      suitable: [''],
      description: [''],
      keyBenefit: [''],
      howToUse: [''],
      ingredients: [''],
      size: [''],
      mrp: [0, Validators.required],
      price: [0, Validators.required],
      stockQuantity: [0, Validators.required],
      discount: [0],
      threeDImages: [''],
      feature: [false],
      trend: [false],
      special: [false],
      specialLine: [''],
      color: [''],
        cards: this.fb.array([]) // Initialize cards as an empty FormArray

    });
  }

 // Getter to easily access the FormArray controls in the template
  get cards(): FormArray {
    return this.productForm.get('cards') as FormArray;
  }


    addCard(): void {
    const cardGroup = this.fb.group({
      text: ['', Validators.required],
      title: ['', Validators.required],
      images: [[]] // Placeholder for card images
    });
    this.cards.push(cardGroup);
  }


  onFileSelected(event: any, type: string, index?: number): void {
    const files: FileList = event.target.files;

    if (type === 'mainImage') {
      this.mainImage = files.item(0);
    } else if (type === 'thumbnail') {
      this.thumbnail = files.item(0);
    } else if (type === 'threeDImages') {
      this.threeDImages = Array.from(files);
    } else if (type === 'images') {
      this.images = Array.from(files);
    } else if (type === 'cards' && index !== undefined) {
      // Handle images specific to each card
      const fileArray: File[] = Array.from(files);
      this.cards.at(index).patchValue({ images: fileArray });
    }
  }
  

  ngOnInit(): void {
      this.loadProducts();
    this.categoryOptions = [];
    this.subCategoryOptions = [];
     this.addCard(); 

  }

  loadProducts(): void {
    this.productService.getAllProducts().subscribe(
      (data: Product[]) => this.products = data,
      error => console.error('Error fetching products', error)
    );
  }

editProduct(product: Product): void {
    this.productForm.patchValue({
      id: product.id,
      favorited: product.favorited,
      rating: product.averageRating,
      shine: product.shine,
      sheShine: product.sheShine,
      subcategory: product.subcategory,
      category: product.category,
      mainImage: product.mainImage,
      cards: product.cards || [], // Ensure cards array is handled properly
      images: product.images || [], // Handle array of images
      threeDImages: product.threeDImages || [], // Handle array of 3D images
      thumbnail: product.thumbnail,
      title: product.title,
      name: product.name,
      benefit: product.benefit,
      suitable: product.suitable,
      description: product.description,
      keyBenefit: product.keyBenefit,
      howToUse: product.howToUse,
      ingredients: product.ingredients,
      size: product.size,
      mrp: product.mrp,
      price: product.price,
      stockQuantity: product.stockQuantity,
      discount: product.discount,
      averageRating: product.averageRating,
      feature: product.feature,
      trend: product.trend,
      special: product.special,
      specialLine: product.specialLine,
      color: product.color
    });

    // this.editingProductId = product.id;
  }

 onSubmit(): void {
  if (this.productForm.valid) {
    // Create FormData object
    const formData = new FormData();
   
    // Append main product data
    formData.append('product', new Blob([JSON.stringify(this.productForm.value)], { type: 'application/json' }));

    // Append individual images to FormData
    if (this.mainImage) {
      formData.append('mainImage', this.mainImage);
    }

    if (this.thumbnail) {
      formData.append('thumbnail', this.thumbnail);
    }

    this.threeDImages.forEach((file, index) => {
      formData.append(`threeDImages[${index}]`, file);
    });

    this.images.forEach((file, index) => {
      formData.append(`images[${index}]`, file);
    });


     // Debugging: Log formData
  formData.forEach((value, key) => {
    console.log(`${key}:`, value);
  });
    // Append each card data with images
    this.cards.controls.forEach((card, index) => {
      formData.append(`cards[${index}][text]`, card.value.text);
      formData.append(`cards[${index}][title]`, card.value.title);

      if (card.value.images && card.value.images.length) {
        card.value.images.forEach((file: File, imgIndex: number) => {
          formData.append(`cards[${index}][images][${imgIndex}]`, file);
        });
      }
    });


    // Call appropriate service method based on whether it's an update or add
    if (this.productForm.value.id) {
      // Update product
      this.productService.updateProduct(this.productForm.value.id, formData).subscribe(
        updatedProduct => {
          const index = this.products.findIndex(p => p.id === updatedProduct.id);
          if (index !== -1) {
            this.products[index] = updatedProduct;
          }
          this.resetForm();
          console.log('Product updated successfully!', updatedProduct);
        },
        error => console.error('Error updating product', error)
      );
    } else {
      // Add product
      this.productService.addProduct(formData).subscribe(
        response => {
          this.resetForm();
          console.log('Product submitted successfully!', response);
        },
        error => console.error('Error submitting product!', error)
      );
    }
  } else {
    console.error('Form is invalid');
  }
}


  resetForm(): void {
    this.productForm.reset();
    this.editingProductId = null;
    this.showCategory = false; // Reset category visibility
    this.showSubCategory = false; // Reset subcategory visibility
    this.categoryOptions = [];
    this.subCategoryOptions = [];
  }

onCheckboxChange(selected: string) {
  

  if (selected === 'shine') {
    this.productForm.get('sheShine')?.setValue(false);
    this.categoryOptions = ['Baby Care', 'body care', 'face care', 'hair care', 'new launches', 'skin care'];
   
  } else if (selected === 'sheShine') {
    this.productForm.get('shine')?.setValue(false);
    this.categoryOptions = ['Necklace', 'Earrings', 'Bracelet', 'Ring', 'Bangle', 'Pendant', 'Brooch', 'Anklet', 'Cufflinks', 'Tiara', 'Choker', 'Charm Bracelet', 'Cameo', 'Locket', 'Toe Ring'];
    
  }

  this.showCategory = this.categoryOptions.length > 0;
  this.showSubCategory = false;
  this.subCategoryOptions = [];

}

 onCategoryChange(event: Event): void {
  const selectElement = event.target as HTMLSelectElement;
  const selectedCategory = selectElement.value;

  // Update subCategoryOptions based on selected category
  this.subCategoryOptions = this.categorySubCategoryMap[selectedCategory] || [];
  this.showSubCategory = this.subCategoryOptions.length > 0;
}


  deleteProduct(id: number | undefined): void {
  if (id === undefined) {
    console.error('Product ID is undefined, cannot delete.');
    return; // Early exit if id is undefined
  }

  if (confirm('Are you sure you want to delete this product?')) {
    this.productService.deleteProduct(id).subscribe(
      () => {
        this.products = this.products.filter(p => p.id !== id);
        console.log('Product deleted successfully');
      },
      error => console.error('Error deleting product', error)
    );
  }
}
}

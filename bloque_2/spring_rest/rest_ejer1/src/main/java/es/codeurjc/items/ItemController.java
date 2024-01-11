package es.codeurjc.items;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private ItemService items;

	@GetMapping("/")
	public Collection<Item> getItems() {
		return items.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Item> getItem(@PathVariable long id) {

		Item item = items.findById(id);

		if (item != null) {
			return ResponseEntity.ok(item);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/")
	public ResponseEntity<Item> createItem(@RequestBody Item item) {

		items.save(item);

		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(item.getId()).toUri();

		return ResponseEntity.created(location).body(item);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Item> replaceItem(@PathVariable long id, @RequestBody Item newItem) {

		Item item = items.findById(id);

		if (item != null) {

			newItem.setId(id);
			items.save(newItem);

			return ResponseEntity.ok(item);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Item> deleteItem(@PathVariable long id) {

		Item item = items.findById(id);

		if (item != null) {
			items.deleteById(id);
			return ResponseEntity.ok(item);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}

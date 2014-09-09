<html>
<head>
	<title>Log In | Signal</title>
	<meta charset="utf-8"/>
</head>
<body lang="en">
	<div class="container">
		<h2>Log In</h2>
		<form action="<?php echo site_url('tunnel/authenticate');?>" method="post">
			<label for="email">Email</label>
			<input type="email" name="email" id="email" placeholder="you@example.com"/>
			<label for="password">Password</label>
			<input type="password" name="password" id="password" placeholder="your password"/>
			<input type="submit" value="Log In"/>
		</form>
	</div>
</body>
</html>
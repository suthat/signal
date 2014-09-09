<html>
<head>
	<title>Dashboard | Signal</title>
	<meta charset="utf-8"/>
</head>
<body lang="en">
	<div class="container">
		<h2>Dashboard</h2>
		<p>
			<b>User</b> <?php $user->id;?> <?php echo $user->name;?> (<?php echo $user->email;?>)
			<a href="<?php echo site_url('tunnel/logout');?>">Log Out</a>
		</p>
		<p>
			<b>App:</b> <?php echo $user->app_title;?><br/> 
			<b>App Excerpt:</b> <?php echo $user->app_excerpt;?><br/> 
			<b>App ID:</b> <em><?php echo $user->app_id;?></em><br/> 
			<b>App Secret:</b> <em><?php echo $user->app_secret;?></em><br/> 
		</p>
	</div>
</body>
</html>